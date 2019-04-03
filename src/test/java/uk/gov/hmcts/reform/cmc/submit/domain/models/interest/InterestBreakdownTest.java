package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class InterestBreakdownTest {

    @Test
    public void shouldBeValidWhenCorrectDataIsProvided() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("It's like that because...");
        interestBreakdown.setTotalAmount(BigDecimal.valueOf(100.75));

        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeInvalidIfTotalAmountIsNull() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("It's like that because...");
        interestBreakdown.setTotalAmount(null);

        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).containsOnly("totalAmount : must not be null");
    }

    @Test
    public void shouldBeInvalidIfExplanationIsNull() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation(null);
        interestBreakdown.setTotalAmount(BigDecimal.valueOf(100.75));

        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).containsOnly("explanation : must not be blank");
    }

    @Test
    public void shouldBeInvalidIfExplanationIsEmpty() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("");
        interestBreakdown.setTotalAmount(BigDecimal.valueOf(100.75));


        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).containsOnly("explanation : must not be blank");
    }

    @Test
    public void shouldBeInvalidWhenNegativeAmountIsProvided() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("It's like that because...");
        interestBreakdown.setTotalAmount(BigDecimal.valueOf(-1));


        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).containsOnly("totalAmount : must be greater than or equal to 0.00");
    }

    @Test
    public void shouldBeValidWhenZeroIsProvidedForAmount() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("It's like that because...");
        interestBreakdown.setTotalAmount(BigDecimal.ZERO);

        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeInValidWhenTooManyFractionDigitsAreProvided() {
        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("It's like that because...");
        interestBreakdown.setTotalAmount(BigDecimal.valueOf(123.456));

        Set<String> validationErrors = validate(interestBreakdown);

        assertThat(validationErrors).containsOnly("totalAmount : can not be more than 2 fractions");
    }

}
