package uk.gov.hmcts.reform.cmc.submit.domain.models;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleParty;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.STANDARD;

public class ClaimInputTest {

    @Test
    public void shouldBeValidWhenGivenInterestIsValid() {
        //given
        Interest validInterest = SampleInterest
                .builder()
                .withType(STANDARD)
                .withRate(new BigDecimal(8))
                .withReason(null)
                .withInterestBreakdown(null)
                .withInterestDate(SampleInterestDate.validDefaults())
                .withSpecificDailyAmount(null)
                .build();
        ClaimInput claimData = SampleClaimData.builder()
            .withInterest(validInterest)
            .build();
        //when
        Set<String> errors = validate(claimData);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenNullDefendants() {
        ClaimInput claimData = SampleClaimData.builder()
            .withDefendants(null)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNoDefendants() {
        ClaimInput claimData = SampleClaimData.builder()
            .clearDefendants()
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNullDefendant() {
        ClaimInput claimData = SampleClaimData.builder()
            .withDefendant(null)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants : each element must be not null");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidDefendant() {
        IndividualDetails individualDetails = SampleTheirDetails.individualDetails();
        individualDetails.setFirstName("");

        ClaimInput claimData = SampleClaimData.builder()
            .withDefendant(individualDetails)
            .build();


        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants[0].firstName : must not be blank");
    }

    @Test
    public void shouldBeValidWhenGivenTwentyDefendants() {
        ClaimInput claimData = SampleClaimData.builder()
            .clearDefendants()
            .addDefendants(SampleTheirDetails.individualDetails(20))
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenNullClaimants() {
        ClaimInput claimData = SampleClaimData.builder()
            .withClaimants(null)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNoClaimants() {
        ClaimInput claimData = SampleClaimData.builder()
            .clearClaimants()
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNullClaimant() {
        ClaimInput claimData = SampleClaimData.builder()
            .withClaimant(null)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants : each element must be not null");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidClaimant() {
        Individual individual = SampleParty.individual();
        individual.setName("");

        ClaimInput claimData = SampleClaimData.builder()
            .withClaimant(individual)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants[0].name : must not be blank");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidClaimantAddress() {
        Individual individual = SampleParty.individual();
        individual.setAddress(null);
        ClaimInput claimData = SampleClaimData.builder()
            .withClaimant(individual)
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants[0].address : must not be null");
    }

    @Test
    public void shouldBeValidWhenGivenTwentyClaimants() {
        ClaimInput claimData = SampleClaimData.builder()
            .clearClaimants()
            .addClaimants(SampleParty.individualDetails(20))
            .build();

        Set<String> errors = validate(claimData);

        assertThat(errors).isEmpty();
    }

}
