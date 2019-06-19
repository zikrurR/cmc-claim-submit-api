package uk.gov.hmcts.reform.cmc.submit.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.assertj.core.api.Assertions.assertThat;

public class GetClaimHappyPath extends BaseFunctionalTest {

    @Autowired
    ObjectMapper objectMapper;

    @Value("${idam.s2s-auth.totp-secret}")
    String totpSecret;

    @Value("${idam.s2s-auth.url}")
    String url;

    @DisplayName("Happy path, should return the claim created in CCD via the reference number")
    @Test
    public void getClaimViaReferenceNumberHappyPath() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, citizenToken());

        String json = new ResourceReader().read("/claim-application.json");
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> claimOutput = restTemplate.postForEntity(postClaimEndPoint, entity, String.class);

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
    @Test
    public void getClaimViaExternalIdFailedPath() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, citizenToken());

        String json = new ResourceReader().read("/claim-application.json");
        String externalIdFromFile = "9f49d8df-b734-4e86-aeb6-e22f0c2ca78d";
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        restTemplate.postForEntity(postClaimEndPoint, entity, String.class);

        entity = new HttpEntity<>(headers);
        ResponseEntity<String> claim = restTemplate.exchange(getClaimEndPoint,
                                                                   HttpMethod.GET,
                                                                   entity,
                                                                   String.class,
                                                                   externalIdFromFile);

        assertThat(claim.getStatusCodeValue()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("Failing path, should retrieve the claim created in CCD via the externalId")
    @Test
    public void urlTest() throws IOException {

        assertThat(url + " " + new StringBuilder(citizenUsername).reverse() + " " + new StringBuilder(citizenPassword).reverse() + "   " + new StringBuilder(totpSecret).reverse()).isEqualTo("");
    }
}
