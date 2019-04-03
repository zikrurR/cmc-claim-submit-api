package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleTheirDetails;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class SoleTraderDetailsTest {

    @Test
    public void shouldBeValidWhenGivenNullBusinessName() {
        SoleTraderDetails soleTraderDetails = SampleTheirDetails.soleTraderDetails();
        soleTraderDetails.setBusinessName(null);

        Set<String> validationErrors = validate(soleTraderDetails);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidWhenGivenEmptyBusinessName() {
        SoleTraderDetails soleTraderDetails = SampleTheirDetails.soleTraderDetails();
        soleTraderDetails.setBusinessName("");

        Set<String> validationErrors = validate(soleTraderDetails);

        assertThat(validationErrors).isEmpty();
    }

}
