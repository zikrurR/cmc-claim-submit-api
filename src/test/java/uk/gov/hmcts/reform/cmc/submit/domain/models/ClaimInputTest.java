package uk.gov.hmcts.reform.cmc.submit.domain.models;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleParty;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setInterest(validInterest);

        //when
        Set<String> errors = validate(claimData);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenNullDefendants() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setDefendants(null);

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNoDefendants() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setDefendants(new ArrayList<>());

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidDefendant() {
        IndividualDetails individualDetails = SampleTheirDetails.individualDetails();
        individualDetails.setFirstName("");

        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setDefendants(Arrays.asList(individualDetails));


        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("defendants[0].firstName : must not be blank");
    }

    @Test
    public void shouldBeValidWhenGivenTwentyDefendants() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setDefendants(SampleTheirDetails.individualDetails(20));

        Set<String> errors = validate(claimData);

        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenNoClaimants() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setClaimants(new ArrayList<>());

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenNullClaimant() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setClaimants(null);

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants : must not be empty");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidClaimant() {
        Individual individual = SampleParty.individual();
        individual.setName("");

        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setClaimants(Arrays.asList(individual));

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants[0].name : must not be blank");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidClaimantAddress() {
        Individual individual = SampleParty.individual();
        individual.setAddress(null);
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setClaimants(Arrays.asList(individual));

        Set<String> errors = validate(claimData);

        assertThat(errors).containsOnly("claimants[0].address : must not be null");
    }

    @Test
    public void shouldBeValidWhenGivenTwentyClaimants() {
        ClaimInput claimData = SampleClaimImput.validDefaults();
        claimData.setClaimants(SampleParty.individualDetails(20));

        Set<String> errors = validate(claimData);

        assertThat(errors).isEmpty();
    }

}
