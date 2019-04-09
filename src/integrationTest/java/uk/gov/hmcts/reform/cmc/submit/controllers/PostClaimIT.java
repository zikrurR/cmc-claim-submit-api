package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

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

import uk.gov.hmcts.reform.authorisation.ServiceAuthorisationHealthApi;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;

import java.util.Map;

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
    private ArgumentCaptor<String> userId;
    @Captor
    private ArgumentCaptor<String> jurisdictionId;
    @Captor
    private ArgumentCaptor<String> caseType;
    @Captor
    private ArgumentCaptor<String> eventId;

    @MockBean
    private ServiceAuthorisationHealthApi serviceAuthorisationHealthApi;

    protected static final String AUTHORIZATION_TOKEN = "Bearer token";
    protected static final String SERVICE_AUTHORIZATION_TOKEN = "aaa";
    @DisplayName("Happy path should create the claim requested with 200 response code")
    @Test
    public void happyPathPostClaim() throws Exception {

        ClaimInput claim = SampleClaimData.validDefaults();

        // mock ccd call
        when(coreCaseDataApi.startForCitizen(any(), any(), any(), any(), any(), any())).thenReturn(StartEventResponse.builder()
                .caseDetails(CaseDetails.builder().data(Maps.newHashMap()).build())
                .eventId("eventId")
                .token("token")
                .build());

        Map<String,Object> mandatoryData = Maps.newHashMap();
        mandatoryData.put("referenceNumber", "ramdom_reference_number");

        when(coreCaseDataApi.submitForCitizen(any(), any(), any(), any(), any(), anyBoolean(), any())).thenReturn(CaseDetails.builder().data(mandatoryData).build());

        // mock idam call
        when(authTokenGenerator.generate()).thenReturn(SERVICE_AUTHORIZATION_TOKEN);

        MvcResult response = mockMvc
                .perform(post("/claim/")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TOKEN)
                        .content(objectMapper.writeValueAsString(claim)))
                    .andExpect(status().isOk())
                    .andReturn();

        ClaimOutput claimResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ClaimOutput.class);

        verify(coreCaseDataApi).startForCitizen(authorizationToken.capture(), serviceAuthorization.capture(), userId.capture(), jurisdictionId.capture(), caseType.capture(), eventId.capture());

        assertThat(claimResponse.getReferenceNumber()).isEqualTo("ramdom_reference_number");

        assertThat(authorizationToken.getValue()).isEqualTo(AUTHORIZATION_TOKEN);
        assertThat(serviceAuthorization.getValue()).isEqualTo(SERVICE_AUTHORIZATION_TOKEN);
        //assertThat(userId.getValue()).isEqualTo(SERVICE_AUTHORIZATION_TOKEN);
        // TODO need to refactor the ccd client


    }
}
