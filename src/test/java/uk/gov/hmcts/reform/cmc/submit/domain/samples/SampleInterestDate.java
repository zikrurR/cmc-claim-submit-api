package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.DatesProvider;

public class SampleInterestDate {

    private SampleInterestDate() {
        super();
    }

    public static InterestDate submission() {

        InterestDate interestDate = new InterestDate();
        interestDate.setEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT);
        interestDate.setType(InterestDate.InterestDateType.SUBMISSION);

        return interestDate;
    }

    public static InterestDate submissionToSubmission() {

        InterestDate interestDate = new InterestDate();
        interestDate.setEndDateType(InterestDate.InterestEndDateType.SUBMISSION);
        interestDate.setType(InterestDate.InterestDateType.SUBMISSION);
        return interestDate;
    }

    public static InterestDate submissionToSettledOrJudgement() {

        InterestDate interestDate = new InterestDate();
        interestDate.setEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT);
        interestDate.setType(InterestDate.InterestDateType.SUBMISSION);

        return interestDate;
    }

    public static InterestDate validDefaults() {

        InterestDate interestDate = new InterestDate();
        interestDate.setDate(DatesProvider.INTEREST_DATE);
        interestDate.setEndDateType(InterestDate.InterestEndDateType.SETTLED_OR_JUDGMENT);
        interestDate.setReason("I want to claim from this date because that's when that happened");
        interestDate.setType(InterestDate.InterestDateType.CUSTOM);

        return interestDate;
    }

}
