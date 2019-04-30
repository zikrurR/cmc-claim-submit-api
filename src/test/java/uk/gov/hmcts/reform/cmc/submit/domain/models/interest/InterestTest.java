package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.BREAKDOWN;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.DIFFERENT;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.STANDARD;

public class InterestTest {

    @Test
    public void shouldBeInvalidWhenAllFieldsAreNull() {
        //given
        Interest interest = new Interest();

        Set<String> errors = validate(interest);
        //then
        assertThat(errors)
                .containsExactly("type : must not be null");
    }

    @Test
    public void withNoInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest.noInterest();

        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withStandardInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest.validDefaults();
        interest.setType(STANDARD);
        interest.setInterestDate(SampleInterestDate.validDefaults());
        interest.setInterestBreakdown(null);
        interest.setReason(null);
        interest.setRate(new BigDecimal(8));
        interest.setSpecificDailyAmount(null);

        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withDifferentInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest.validDefaults();
        interest.setType(DIFFERENT);
        interest.setInterestDate(SampleInterestDate.validDefaults());
        interest.setInterestBreakdown(null);
        interest.setReason("reason");
        interest.setRate(new BigDecimal(8));
        interest.setSpecificDailyAmount(null);

        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSettleOrJudgmentEndDateType_shouldBeSuccessfulWhenRateProvided() {
        InterestDate interestDateOfTypeSettledOrJudgment = SampleInterestDate.validDefaults();
        interestDateOfTypeSettledOrJudgment.setEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT);

        Interest interest = SampleInterest.validDefaults();
        interest.setType(BREAKDOWN);
        interest.setInterestDate(interestDateOfTypeSettledOrJudgment);
        interest.setInterestBreakdown(SampleInterestBreakdown.validDefaults());
        interest.setReason(null);
        interest.setRate(new BigDecimal(8));
        interest.setSpecificDailyAmount(null);

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSettleOrJudgmentEndDateType_shouldBeSuccessfulWhenSpecDailyAmntProvided() {
        InterestDate interestDateOfTypeSettledOrJudgment = SampleInterestDate.validDefaults();
        interestDateOfTypeSettledOrJudgment.setEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT);

        Interest interest = SampleInterest.validDefaults();
        interest.setType(BREAKDOWN);
        interest.setInterestDate(interestDateOfTypeSettledOrJudgment);
        interest.setInterestBreakdown(SampleInterestBreakdown.validDefaults());
        interest.setReason(null);
        interest.setRate(null);
        interest.setSpecificDailyAmount(new BigDecimal(10));

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSubmissionEndDateType_shouldBeSuccessful() {
        InterestDate interestDateOfTypeSubmission = SampleInterestDate.validDefaults();
        interestDateOfTypeSubmission.setEndDateType(InterestDate.InterestEndDateType.SUBMISSION);

        Interest interest = SampleInterest.validDefaults();
        interest.setType(BREAKDOWN);
        interest.setInterestDate(interestDateOfTypeSubmission);
        interest.setInterestBreakdown(SampleInterestBreakdown.validDefaults());
        interest.setReason(null);
        interest.setRate(null);
        interest.setSpecificDailyAmount(null);

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

}
