package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAddress;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class TheirDetailsTest {

    @Test
    public void shouldBeInvalidWhenGivenNullName() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setName(null);

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("name : must not be blank");
    }

    @Test
    public void shouldBeInvalidWhenGivenEmptyName() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setName("");

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("name : must not be blank");
    }

    @Test
    public void shouldBeInvalidWhenGivenNullAddress() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setAddress(null);

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("address : must not be null");
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidAddress() {

        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setAddress(SampleAddress.invalidDefaults());

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("address.postcode : Postcode is not of valid format");
    }

    @Test
    public void shouldBeValidWhenGivenNullEmail() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setEmail(null);

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidEmail() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setEmail("this is not a valid email address");

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("email : must be a well-formed email address");
    }

    @Test
    public void shouldBeInvalidWhenGivenNonTrimmedEmail() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setEmail(" user@example.com ");

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("email : must be a well-formed email address");
    }

    @Test
    public void shouldBeValidWhenGivenNullServiceAddress() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setServiceAddress(null);

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGivenInvalidServiceAddress() {
        TheirDetails theirDetails = SampleTheirDetails.partyDetails();
        theirDetails.setServiceAddress(SampleAddress.invalidDefaults());

        Set<String> validationErrors = validate(theirDetails);

        assertThat(validationErrors)
            .hasSize(1)
            .contains("serviceAddress.postcode : Postcode is not of valid format");
    }
}
