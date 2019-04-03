package uk.gov.hmcts.reform.cmc.submit.domain.models.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.config.JacksonConfiguration;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleParty;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.ResourceReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest.standardInterestBuilder;

public class ClaimDataSerializationTest {

    @Test
    public void shouldConvertJsonToJava() throws IOException {
        //given
        String input = new ResourceReader().read("/claim-application.json");
        ObjectMapper mapper = new JacksonConfiguration().objectMapper();

        //when
        ClaimData claimData = mapper.readValue(input, ClaimData.class);

        //then
        Individual claimant = SampleParty.individual();
        claimant.setRepresentative(null);

        IndividualDetails defendent = SampleTheirDetails.individualDetails();
        defendent.setRepresentative(null);
        defendent.setServiceAddress(null);
        defendent.setDateOfBirth(null);

        ClaimData other = SampleClaimData.builder()
            .withExternalId(UUID.fromString("9f49d8df-b734-4e86-aeb6-e22f0c2ca78d"))
            .withInterest(
                standardInterestBuilder()
                    .withInterestDate(
                        SampleInterestDate.builder()
                            .withDate(LocalDate.of(2015, 2, 2))
                            .build())
                    .build())
            .withPreferredCourt(null)
            .withHousingDisrepair(null)
            .withPersonalInjury(null)
            .withStatementOfTruth(null)
            .clearClaimants()
            .addClaimant(claimant)
            .clearDefendants()
            .addDefendant(defendent)
            .build();

        assertThat(claimData).isEqualTo(other);
    }
}
