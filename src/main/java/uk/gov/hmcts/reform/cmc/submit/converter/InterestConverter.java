package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate.InterestEndDateType;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class InterestConverter {

    public Interest from(CcdCase ccdCase) {
        if (ccdCase.getInterestType() == null
            && ccdCase.getInterestRate() == null
            && isBlank(ccdCase.getInterestReason())
            && ccdCase.getInterestSpecificDailyAmount() == null
            && ccdCase.getInterestBreakDownAmount() == null
            && isBlank(ccdCase.getInterestBreakDownExplanation())
            && ccdCase.getInterestDateType() == null
        ) {
            return null;
        }


        Interest interest = new Interest();
        interest.setInterestBreakdown(interestBreakdownFrom(ccdCase));
        interest.setReason(ccdCase.getInterestReason());
        interest.setInterestDate(interestDateFrom(ccdCase));
        interest.setRate(ccdCase.getInterestRate());
        interest.setSpecificDailyAmount(ccdCase.getInterestSpecificDailyAmount());
        interest.setType(Interest.InterestType.valueOf(ccdCase.getInterestType().name()));

        return interest;
    }


    public InterestBreakdown interestBreakdownFrom(CcdCase ccdCase) {
        if (ccdCase.getInterestBreakDownAmount() == null && ccdCase.getInterestBreakDownExplanation() == null) {
            return null;
        }

        InterestBreakdown interestBreakdown = new InterestBreakdown();

        interestBreakdown.setExplanation(ccdCase.getInterestBreakDownExplanation());
        interestBreakdown.setTotalAmount(ccdCase.getInterestBreakDownAmount());

        return interestBreakdown;
    }


    public InterestDate interestDateFrom(CcdCase ccdCase) {
        if (ccdCase.getInterestDateType() == null) {
            return null;
        }

        InterestEndDateType endDateType = ccdCase.getInterestDateType() != null
            ? InterestEndDateType.valueOf(ccdCase.getInterestEndDateType().name())
            : null;

        InterestDate interestDate = new InterestDate();

        interestDate.setDate(ccdCase.getInterestClaimStartDate());
        interestDate.setEndDateType(endDateType);
        interestDate.setReason(ccdCase.getInterestStartDateReason());
        interestDate.setType(InterestDate.InterestDateType.valueOf(ccdCase.getInterestDateType().name()));

        return interestDate;
    }
}
