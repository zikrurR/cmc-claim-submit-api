package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;

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

    protected static final String AUTHORISATION_TOKEN = "Bearer token";

    @DisplayName("Happy path to generate an error during the claim creation."
               + " it should return an json error with 400 response code")
    @Test
    public void happyPathPostClaim() throws Exception {

        ClaimInput claim = SampleClaimImput.validDefaults();

        // set the claimant name to blank to generate the fail
        claim.getClaimants().get(0).setName("");

        mockMvc
                .perform(post("/claim/")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN)
                        .content(objectMapper.writeValueAsString(claim)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("[0].rejectedValue").value(""));

    }
}
