package uk.gov.hmcts.reform.cmc.submit.domain.models.amount;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAmountBreakdown;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class AmountBreakDownTest {

    @Test
    public void shouldBeSuccessfulValidationForFullAmountDetails() {
        //given
        AmountBreakDown amountBreakDown = SampleAmountBreakdown.validDefaults();
        //when
        Set<String> validationMessages = validate(amountBreakDown);
        //then
        assertThat(validationMessages).isEmpty();
    }

    @Test
    public void shouldReturnValidationMessageWhenAmountBreakDownHasNullRows() {
        //given
        AmountBreakDown amountBreakDown = SampleAmountBreakdown.validDefaults();
        amountBreakDown.setRows(null);

        //when
        Set<String> validationMessages = validate(amountBreakDown);
        //then
        assertThat(validationMessages)
            .hasSize(1)
            .contains("rows : must not be null");
    }

}
