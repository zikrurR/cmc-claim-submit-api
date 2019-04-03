package uk.gov.hmcts.reform.cmc.submit.domain.models.common;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleContactDetails;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class ContactDetailsTest {

    @Test
    public void shouldBeValidForValidObject() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidForNullValues() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();
        contactDetails.setPhone(null);
        contactDetails.setEmail(null);
        contactDetails.setDxAddress(null);

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidForEmptyEmailAndDxAddressValues() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();
        contactDetails.setEmail("");
        contactDetails.setDxAddress("");

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void phoneNumberShouldNotBeValidForEmptyValue() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();
        contactDetails.setPhone("");

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).containsOnly("phone : must be a valid UK phone number");
    }

    @Test
    public void shouldBeInvalidForInvalidPhoneNumber() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();
        contactDetails.setPhone("123");

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).containsOnly("phone : must be a valid UK phone number");
    }

    @Test
    public void shouldBeInvalidForInvalidEmail() {
        ContactDetails contactDetails = SampleContactDetails.validDefaults();
        contactDetails.setEmail("this is not a valid email");

        Set<String> validationErrors = validate(contactDetails);

        assertThat(validationErrors).containsOnly("email : must be a well-formed email address");
    }

}
