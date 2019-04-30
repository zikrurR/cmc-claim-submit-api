package uk.gov.hmcts.reform.cmc.submit.domain.models.claimants;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleParty;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class PartyTest {

    @Test
    public void shouldReturnValidationErrorsWhenNameIsNull() {
        Party party = SampleParty.validDefaults();
        party.setName(null);

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("name : must not be blank");
    }

    @Test
    public void shouldReturnValidationErrorsWhenNameIsEmpty() {
        Party party = SampleParty.validDefaults();
        party.setName("");

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("name : must not be blank");
    }

    @Test
    public void shouldReturnValidationErrorsWhenNameIsTooLong() {
        Party party = SampleParty.validDefaults();
        party.setName(StringUtils.repeat("nana", 200));

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("name : may not be longer than 255 characters");
    }

    @Test
    public void shouldReturnValidationErrorsWhenGivenNullAddress() {
        Party party = SampleParty.validDefaults();
        party.setAddress(null);

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("address : must not be null");
    }

    @Test
    public void shouldReturnValidationErrorsWhenGivenInvalidAddress() {
        Party party = SampleParty.validDefaults();
        party.getAddress().setPostcode("");

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors).hasSize(1);
    }

    @Test
    public void shouldBeValidWhenGivenNotGivenCorrespondenceAddress() {
        Party party = SampleParty.validDefaults();
        party.setCorrespondenceAddress(null);

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldReturnNoValidationErrorsWhenGivenNullMobilePhone() {
        Party party = SampleParty.validDefaults();
        party.setMobilePhone(null);

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(0);
    }

    @Test
    public void shouldReturnNoValidationErrorsWhenGivenValidMobilePhone() {
        Party party = SampleParty.validDefaults();
        party.setMobilePhone("07987654321");

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(0);
    }

    @Test
    public void shouldReturnValidationErrorsWhenGivenInvalidMobilePhone() {
        Party party = SampleParty.validDefaults();
        party.setMobilePhone("1234567890123456789012345678901");

        Set<String> validationErrors = validate(party);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("mobilePhone : may not be longer than 30 characters");
    }

}
