package uk.gov.hmcts.reform.cmc.submit.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleParty;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.ResourceReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ClaimDataSerializationTest {

    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void shouldConvertJsonToJava() throws IOException {

        Individual claimant = SampleParty.individual();
        claimant.setRepresentative(null);

        IndividualDetails defendent = SampleTheirDetails.individualDetails();
        defendent.setRepresentative(null);
        defendent.setServiceAddress(null);
        defendent.setDateOfBirth(null);

        ClaimInput other = SampleClaimImput.validDefaults();
        other.setExternalId(UUID.fromString("9f49d8df-b734-4e86-aeb6-e22f0c2ca78d"));

        Interest standard = SampleInterest.standard();
        standard.getInterestDate().setDate(LocalDate.of(2015, 2, 2));
        other.setInterest(standard);

        other.setPreferredCourt(null);
        other.setHousingDisrepair(null);
        other.setPersonalInjury(null);
        other.setStatementOfTruth(null);
        other.setClaimants(Arrays.asList(claimant));
        other.setDefendants(Arrays.asList(defendent));

        String input = new ResourceReader().read("/claim-application.json");
        ObjectMapper mapper = objectMapper();

        ClaimInput claimData = mapper.readValue(input, ClaimInput.class);

        assertThat(claimData).isEqualTo(other);
    }
}
