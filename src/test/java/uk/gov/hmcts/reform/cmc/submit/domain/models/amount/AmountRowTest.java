package uk.gov.hmcts.reform.cmc.submit.domain.models.amount;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAmountRow;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class AmountRowTest {

    @Test
    public void shouldBeSuccessfulValidationForFullAmountDetails() {
        //given
        AmountRow amountRow = SampleAmountRow.validDefaults();
        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldReturnValidationMessageWhenAmountHasValueLessThanMinimum() {
        //given
        AmountRow amountRow = SampleAmountRow.validDefaults();
        amountRow.setAmount(new BigDecimal("0.00"));
        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors)
            .hasSize(1)
            .contains("amount : must be greater than or equal to 0.01");
    }

    @Test
    public void shouldBeSuccessfulValidationForFullAmountDetailsWithTwoFraction() {
        //given
        AmountRow amountRow = SampleAmountRow.validDefaults();
        amountRow.setAmount(new BigDecimal("40.50"));
        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldReturnValidationMessageWhenAmountHasValueWithMoreThanAllowedFractions() {
        //given
        AmountRow amountRow = SampleAmountRow.validDefaults();
        amountRow.setAmount(new BigDecimal("40.521"));
        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors)
            .hasSize(1)
            .contains("amount : can not be more than 2 fractions");
    }
}
