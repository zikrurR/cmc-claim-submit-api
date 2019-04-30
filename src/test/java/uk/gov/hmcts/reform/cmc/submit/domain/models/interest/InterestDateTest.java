package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleInterestDate;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;

public class InterestDateTest {

    @Test
    public void withCustomType_shouldBeSuccessfulValidationWhenDateInThePast() {
        //given

        InterestDate interestDate = SampleInterestDate.validDefaults();
        interestDate.setDate(LocalDate.of(2015, 2, 5));

        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withSubmissionType_shouldBeSuccessfulValidation() {
        //given
        InterestDate interestDate = SampleInterestDate.submissionToSubmission();
        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }

    @Test
    public void withNullType_shouldBeSuccessfulValidation() {
        //given
        InterestDate interestDate = SampleInterestDate.submissionToSubmission();
        interestDate.setType(null);

        //when
        Set<String> errors = validate(interestDate);
        //then
        assertThat(errors).isEmpty();
    }
}
