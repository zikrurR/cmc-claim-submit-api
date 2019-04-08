package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestDateType;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestEndDateType;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestType;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;

@Component
class MergeCaseDataInterest implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        Interest interest = claim.getInterest();
        if (interest == null) {
            return;
        }

        ccdCase.setInterestSpecificDailyAmount(interest.getSpecificDailyAmount());
        interestBreakdown(ccdCase, interest.getInterestBreakdown());
        interestDate(interest.getInterestDate(), ccdCase);

        ccdCase.setInterestType(CcdInterestType.valueOf(interest.getType().name()));
        ccdCase.setInterestRate(interest.getRate());
        ccdCase.setInterestReason(interest.getReason());
    }

    public void interestBreakdown(CcdCase ccdCase, InterestBreakdown interestBreakdown) {
        if (interestBreakdown == null) {
            return;
        }

        ccdCase.setInterestBreakDownAmount(interestBreakdown.getTotalAmount());
        ccdCase.setInterestBreakDownExplanation(interestBreakdown.getExplanation());
    }

    public void interestDate(InterestDate interestDate, CcdCase ccdCase) {
        if (interestDate == null || interestDate.getType() == null) {
            return;
        }

        ccdCase.setInterestDateType(CcdInterestDateType.valueOf(interestDate.getType().name()));
        ccdCase.setInterestClaimStartDate(interestDate.getDate());
        ccdCase.setInterestStartDateReason(interestDate.getReason());
        ccdCase.setInterestEndDateType(CcdInterestEndDateType.valueOf(interestDate.getEndDateType().name()));
    }
}
