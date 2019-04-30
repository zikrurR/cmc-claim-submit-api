package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;

@Component
class MergeCaseDataInterest implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        Interest interest = claim.getInterest();

        ccdCaseBuilder.interestSpecificDailyAmount(interest.getSpecificDailyAmount());
        interestBreakdown(interest.getInterestBreakdown(), ccdCaseBuilder);
        interestDate(interest.getInterestDate(), ccdCaseBuilder);

        ccdCaseBuilder.interestType(CcdInterestType.valueOf(interest.getType().name()));
        ccdCaseBuilder.interestRate(interest.getRate());
        ccdCaseBuilder.interestReason(interest.getReason());
    }

    public void interestBreakdown(InterestBreakdown interestBreakdown, CcdCaseBuilder ccdCaseBuilder) {
        if (interestBreakdown == null) {
            return;
        }

        ccdCaseBuilder.interestBreakDownAmount(interestBreakdown.getTotalAmount());
        ccdCaseBuilder.interestBreakDownExplanation(interestBreakdown.getExplanation());
    }

    public void interestDate(InterestDate interestDate, CcdCaseBuilder ccdCaseBuilder) {
        if (interestDate == null || interestDate.getType() == null) {
            return;
        }

        ccdCaseBuilder.interestDateType(CcdInterestDateType.valueOf(interestDate.getType().name()));
        ccdCaseBuilder.interestClaimStartDate(interestDate.getDate());
        ccdCaseBuilder.interestStartDateReason(interestDate.getReason());
        ccdCaseBuilder.interestEndDateType(CcdInterestEndDateType.valueOf(interestDate.getEndDateType().name()));
    }
}
