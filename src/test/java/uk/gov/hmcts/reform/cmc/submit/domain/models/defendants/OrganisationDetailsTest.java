package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class OrganisationDetailsTest {

    @Test
    public void shouldBeValidWhenGivenNullContactPerson() {
        OrganisationDetails organisationDetails = SampleTheirDetails.organisationDetails();
        organisationDetails.setContactPerson(null);

        Set<String> validationErrors = validate(organisationDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidWhenGivenEmptyContactPerson() {
        OrganisationDetails organisationDetails = SampleTheirDetails.organisationDetails();
        organisationDetails.setContactPerson("");

        Set<String> validationErrors = validate(organisationDetails);

        assertThat(validationErrors).isEmpty();
    }

}
