package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.hmcts.reform.authorisation.ServiceAuthorisationHealthApi;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorMappingIT {

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

    protected static final String AUTHORISATION_TOKEN = "Bearer token";

    @DisplayName("Happy path to generate an error during the claim creation. it should return an json error with 400 response code")
    @Test
    public void happyPathPostClaim() throws Exception {

        ClaimInput claim = SampleClaimData.validDefaults();

        // set the claimant name to blank to generate the fail
        claim.getClaimants().get(0).setName("");

        mockMvc
                .perform(post("/claim/")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN)
                        .content(objectMapper.writeValueAsString(claim)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("[0].rejectedValue").value(""));

        // format not define yet
    }
}
