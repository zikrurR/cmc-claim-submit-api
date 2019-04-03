package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate.InterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate.InterestDateType.CUSTOM;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate.InterestDateType.SUBMISSION;

public class InterestDateTest {

    @Test
    public void withCustomType_shouldBeSuccessfulValidationWhenDateInThePast() {
        //given
        InterestDate interestDate = SampleInterestDate.builder()
                .withType(CUSTOM)
                .withDate(LocalDate.of(2015, 2, 5))
                .withReason("reason")
                .withEndDateType(InterestEndDateType.SUBMISSION)
                .build();
        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withSubmissionType_shouldBeSuccessfulValidation() {
        //given
        InterestDate interestDate = SampleInterestDate.builder()
                .withType(SUBMISSION)
                .withDate(null)
                .withReason(null)
                .withEndDateType(InterestEndDateType.SUBMISSION)
                .build();
        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withNullType_shouldBeSuccessfulValidation() {
        //given
        InterestDate interestDate = SampleInterestDate.builder()
                .withType(null)
                .withDate(null)
                .withReason(null)
                .withEndDateType(InterestEndDateType.SUBMISSION)
                .build();
        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }
}
