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

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetClaimIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoreCaseDataApi coreCaseDataApi;

    @MockBean
    private AuthTokenGenerator authTokenGenerator;

    protected static final String AUTHORISATION_TOKEN = "Bearer token";

    @DisplayName("Happy path should return a claim requested with 200 response code")
    @Test
    public void happyPathGetClaim() throws Exception {

        // mock ccd call
        Map<String,Object> mandatoryData = Maps.newHashMap();
        String externalId = UUID.randomUUID().toString();
        mandatoryData.put("externalId", externalId);
        mandatoryData.put("amountType", "NOT_KNOWN");
        mandatoryData.put("referenceNumber", "random_reference_number");

        when(coreCaseDataApi.searchCases(any(), any(), any(), any()))
             .thenReturn(SearchResult.builder().total(1)
                                               .cases(Arrays.asList(CaseDetails.builder().data(mandatoryData).build()))
                                               .build());

        // mock idam call
        when(authTokenGenerator.generate()).thenReturn("aaa");


        String claimData = objectMapper.writeValueAsString(SampleClaimData.validDefaults());
        MvcResult response = mockMvc
                .perform(get("/claim/{externalId}",externalId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN)
                        .content(claimData))
                    .andExpect(status().isOk())
                    .andReturn();

        Claim claim = objectMapper.readValue(response.getResponse().getContentAsString(), Claim.class);

        assertThat(claim.getAmount().getClass()).isEqualTo(NotKnown.class);

    }
}
