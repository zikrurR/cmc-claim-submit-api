package uk.gov.hmcts.reform.cmc.submit.domain.models.amount;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAmountRange;

import java.util.Set;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class AmountRangeTest {
    @Test
    public void shouldBeSuccessfulValidationForValidAmountDetails() {
        //given
        AmountRange amountRow = SampleAmountRange.validDefaults();
        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeSuccessfulValidationForMaximumValue() {
        //given
        AmountRange amountRow = SampleAmountRange.validDefaults();
        amountRow.setHigherValue(valueOf(9999999.99));
        amountRow.setLowerValue(valueOf(9999999.99));

        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldBeSuccessfulValidationForMinimum() {
        //given
        AmountRange amountRow = SampleAmountRange.validDefaults();
        amountRow.setHigherValue(valueOf(0.01));
        amountRow.setLowerValue(valueOf(0.01));

        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldHaveErrorsForValueHigherThanMaximum() {
        //given
        AmountRange amountRow = SampleAmountRange.validDefaults();
        amountRow.setHigherValue(valueOf(10000000));

        //when
        Set<String> errors = validate(amountRow);
        //then
        assertThat(errors).containsExactlyInAnyOrder("higherValue : can not be more than 2 fractions");
    }

    @Test
    public void shouldHaveErrorsForValueLowerThanMinimum() {
        //given
        AmountRange amountRow = SampleAmountRange.validDefaults();
        amountRow.setHigherValue(valueOf(0));
        amountRow.setLowerValue(valueOf(0));

        //when
        Set<String> errors = validate(amountRow);
        //then

        String[] expectedErroMessages = {"higherValue : must be greater than or equal to 0.01",
            "lowerValue : must be greater than or equal to 0.01"};

        assertThat(errors).containsExactlyInAnyOrder(expectedErroMessages);
    }
}
