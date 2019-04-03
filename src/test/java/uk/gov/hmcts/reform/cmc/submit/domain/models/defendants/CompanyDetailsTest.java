package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class CompanyDetailsTest {

    @Test
    public void shouldBeValidWhenGivenNullContactPerson() {
        CompanyDetails companyDetails = SampleTheirDetails.companyDetails();
        companyDetails.setContactPerson(null);

        Set<String> validationErrors = validate(companyDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidWhenGivenEmptyContactPerson() {
        CompanyDetails companyDetails = SampleTheirDetails.companyDetails();
        companyDetails.setContactPerson("");

        Set<String> validationErrors = validate(companyDetails);

        assertThat(validationErrors).isEmpty();
    }

}
