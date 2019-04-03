package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterest;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.BREAKDOWN;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.DIFFERENT;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.NO_INTEREST;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest.InterestType.STANDARD;

public class InterestTest {

    @Test
    public void shouldBeInvalidWhenAllFieldsAreNull() {
        //given
        Interest interest = SampleInterest
                .builder()
                .withType(null)
                .withRate(null)
                .withReason(null)
                .withInterestBreakdown(null)
                .withInterestDate(null)
                .build();
        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors)
                .containsExactly("type : must not be null");
    }

    @Test
    public void withNoInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest
                .builder()
                .withType(NO_INTEREST)
                .withRate(null)
                .withReason(null)
                .withInterestBreakdown(null)
                .withInterestDate(null)
                .build();
        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withStandardInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest
                .builder()
                .withType(STANDARD)
                .withRate(new BigDecimal(8))
                .withReason(null)
                .withInterestBreakdown(null)
                .withInterestDate(SampleInterestDate.validDefaults())
                .withSpecificDailyAmount(null)
                .build();
        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withDifferentInterestType_shouldBeSuccessful() {
        //given
        Interest interest = SampleInterest
                .builder()
                .withType(DIFFERENT)
                .withRate(new BigDecimal(8))
                .withReason("reason")
                .withInterestDate(SampleInterestDate.validDefaults())
                .withInterestBreakdown(null)
                .withSpecificDailyAmount(null)
                .build();
        //when
        Set<String> errors = validate(interest);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSettleOrJudgmentEndDateType_shouldBeSuccessfulWhenRateProvided() {
        InterestDate interestDateOfTypeSettledOrJudgment = SampleInterestDate
                .builder()
                .withEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT)
                .build();
        Interest interest = SampleInterest
                .builder()
                .withType(BREAKDOWN)
                .withInterestDate(interestDateOfTypeSettledOrJudgment)
                .withInterestBreakdown(SampleInterestBreakdown.validDefaults())
                .withReason(null)
                .withRate(new BigDecimal(8))
                .withSpecificDailyAmount(null)
                .build();

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSettleOrJudgmentEndDateType_shouldBeSuccessfulWhenSpecDailyAmntProvided() {
        InterestDate interestDateOfTypeSettledOrJudgment = SampleInterestDate
                .builder()
                .withEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT)
                .build();
        Interest interest = SampleInterest
                .builder()
                .withType(BREAKDOWN)
                .withInterestDate(interestDateOfTypeSettledOrJudgment)
                .withInterestBreakdown(SampleInterestBreakdown.validDefaults())
                .withReason(null)
                .withRate(null)
                .withSpecificDailyAmount(new BigDecimal(10))
                .build();

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

    @Test
    public void withBreakdownInterestTypeAndSubmissionEndDateType_shouldBeSuccessful() {
        InterestDate interestDateOfTypeSubmission = SampleInterestDate
                .builder()
                .withEndDateType(InterestDate.InterestEndDateType.SUBMISSION)
                .build();
        Interest interest = SampleInterest
                .builder()
                .withType(BREAKDOWN)
                .withInterestDate(interestDateOfTypeSubmission)
                .withInterestBreakdown(SampleInterestBreakdown.validDefaults())
                .withReason(null)
                .withRate(null)
                .withSpecificDailyAmount(null)
                .build();

        Set<String> errors = validate(interest);

        assertThat(errors).isEmpty();
    }

}
