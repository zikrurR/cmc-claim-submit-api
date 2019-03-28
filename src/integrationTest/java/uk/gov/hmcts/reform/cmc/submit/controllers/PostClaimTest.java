package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.reform.authorisation.ServiceAuthorisationHealthApi;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleClaimData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@ImportAutoConfiguration(exclude = {FeignAutoConfiguration.class,
//                                    HealthAutoConfiguration.class,
//                                    HealthIndicatorAutoConfiguration.class})
public class PostClaimTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoreCaseDataApi coreCaseDataApi;

    @MockBean
    private AuthTokenGenerator authTokenGenerator;

    @MockBean
    private ServiceAuthorisationHealthApi serviceAuthorisationHealthApi;

//    @Captor
//    private ArgumentCaptor<String> senderArgument;
//
//    @Captor
//    private ArgumentCaptor<CaseDataContent> caseDataContentArgument;


    protected static final String AUTHORISATION_TOKEN = "Bearer token";

    @DisplayName("Happy path should create the claim requested with 200 response code")
    @Test
    public void postClaim() throws Exception {

        ClaimData claim = SampleClaimData.validDefaults();

        when(coreCaseDataApi.startForCitizen(any(), any(), any(), any(), any(), any())).thenReturn(StartEventResponse.builder()
                .caseDetails(CaseDetails.builder().data(Maps.newHashMap()).build())
                .eventId("eventId")
                .token("token")
                .build());

        when(coreCaseDataApi.submitForCitizen(any(), any(), any(), any(), any(), anyBoolean(), any())).thenReturn(CaseDetails.builder().data(Maps.newHashMap()).build());

        when(authTokenGenerator.generate()).thenReturn("aaa");

        MvcResult response = mockMvc
                .perform(post("/claim/")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN)
                        .content(objectMapper.writeValueAsString(claim)))
                    .andExpect(status().isOk())
                    .andReturn();

        ClaimData claimResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ClaimData.class);

        //assertThat(claimResponse).isEqualTo(ccdCase);

    }
}
