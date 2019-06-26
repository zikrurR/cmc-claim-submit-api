package uk.gov.hmcts.reform.cmc.submit.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import uk.gov.hmcts.reform.cmc.submit.BaseFunctionalTest;
import uk.gov.hmcts.reform.cmc.submit.utils.ResourceReader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GetClaimHappyPath extends BaseFunctionalTest {

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Happy path, should return the claim created in CCD via the reference number")
    @Test
    public void getClaimViaReferenceNumberHappyPath() throws IOException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, citizenToken());

        String json = new ResourceReader().read("/claim-application.json");
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> claimOutput = restTemplate.postForEntity(postClaimEndPoint, entity, String.class);
        TimeUnit.SECONDS.sleep(2);

        Map readValue = objectMapper.readValue(claimOutput.getBody(), Map.class);
        entity = new HttpEntity<>(headers);
        ResponseEntity<String> claim = restTemplate.exchange(getClaimEndPoint,
                                                                   HttpMethod.GET,
                                                                   entity,
                                                                   String.class,
                                                                   readValue.get("referenceNumber"));


        assertThat(claim.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @DisplayName("Failing path, should retrieve the claim created in CCD via the externalId")
    @Ignore // more then one claim return for the same externalId - duplication issue 
    @Test
    public void getClaimViaExternalIdFailedPath() throws IOException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, citizenToken());

        String json = new ResourceReader().read("/claim-application.json");
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        restTemplate.postForEntity(postClaimEndPoint, entity, String.class);
        TimeUnit.SECONDS.sleep(60);

        String externalIdFromFile = "9f49d8df-b734-4e86-aeb6-e22f0c2ca78d";
        entity = new HttpEntity<>(headers);
        ResponseEntity<String> claim = restTemplate.exchange(getClaimEndPoint,
                                                                   HttpMethod.GET,
                                                                   entity,
                                                                   String.class,
                                                                   externalIdFromFile);

        assertThat(claim.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
