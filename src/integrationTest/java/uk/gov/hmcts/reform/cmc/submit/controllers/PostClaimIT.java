package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;

import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostClaimIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoreCaseDataApi coreCaseDataApi;

    @MockBean
    private AuthTokenGenerator authTokenGenerator;

    @Captor
    private ArgumentCaptor<String> authorizationToken;
    @Captor
    private ArgumentCaptor<String> serviceAuthorization;
    @Captor
    private ArgumentCaptor<String> caseType;
    @Captor
    private ArgumentCaptor<String> eventId;

    protected static final String AUTHORIZATION_TOKEN = "Bearer token";
    protected static final String SERVICE_AUTHORIZATION_TOKEN = "aaa";

    @DisplayName("Happy path should create the claim requested with 200 response code")
    @Test
    public void happyPathPostClaim() throws Exception {

        // mock ccd call
        String compact = Jwts.builder()
                .setId("1")
                .setSubject("1")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,
                          new SecretKeySpec(DatatypeConverter.parseBase64Binary("AAAAAAAAAA"),
                                            SignatureAlgorithm.HS256.getJcaName()))
                .compact();

        when(coreCaseDataApi.startCase(any(), any(), any(), any())).thenReturn(StartEventResponse.builder()
                .caseDetails(CaseDetails.builder().data(Maps.newHashMap()).build())
                .eventId("eventId")
                .token(compact)
                .build());

        Map<String,Object> mandatoryData = Maps.newHashMap();
        mandatoryData.put("referenceNumber", "ramdom_reference_number");

        when(coreCaseDataApi.submitForCitizen(any(), any(), any(), any(), any(), anyBoolean(), any()))
            .thenReturn(CaseDetails.builder().data(mandatoryData).build());

        // mock idam call
        when(authTokenGenerator.generate()).thenReturn(SERVICE_AUTHORIZATION_TOKEN);

        String claimData = objectMapper.writeValueAsString(SampleClaimData.validDefaults());
        MvcResult response = mockMvc
                .perform(post("/claim/")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TOKEN)
                        .content(claimData))
                    .andExpect(status().isCreated())
                    .andReturn();

        String contentAsString = response.getResponse().getContentAsString();
        ClaimOutput claimResponse = objectMapper.readValue(contentAsString, ClaimOutput.class);

        verify(coreCaseDataApi).startCase(authorizationToken.capture(),
                                          serviceAuthorization.capture(),
                                          caseType.capture(),
                                          eventId.capture());

        assertThat(claimResponse.getReferenceNumber()).isEqualTo("ramdom_reference_number");

        assertThat(authorizationToken.getValue()).isEqualTo(AUTHORIZATION_TOKEN);
        assertThat(serviceAuthorization.getValue()).isEqualTo(SERVICE_AUTHORIZATION_TOKEN);

    }
}
