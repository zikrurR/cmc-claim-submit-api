package uk.gov.hmcts.reform.cmc.submit.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerPublisherIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Generate swagger 2.0 documentation")
    void generateDocs() throws Exception {
        mvc.perform(get("/v2/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("swagger").value("2.0"));
    }
}
