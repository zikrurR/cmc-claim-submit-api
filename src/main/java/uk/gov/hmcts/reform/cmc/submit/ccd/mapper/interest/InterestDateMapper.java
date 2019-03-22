package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.ccd.domain.CCDInterestDateType;
import uk.gov.hmcts.cmc.ccd.domain.CCDInterestEndDateType;
import uk.gov.hmcts.cmc.domain.models.interest.InterestDate;
import uk.gov.hmcts.cmc.domain.models.interest.InterestDate.InterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

@Component
public class InterestDateMapper implements BuilderMapper<CCDCase, InterestDate, CCDCase.CCDCaseBuilder> {

    @Override
    public void to(InterestDate interestDate, CCDCase.CCDCaseBuilder builder) {
        if (interestDate == null || interestDate.getType() == null) {
            return;
        }

        builder.interestDateType(CCDInterestDateType.valueOf(interestDate.getType().name()))
            .interestClaimStartDate(interestDate.getDate())
            .interestStartDateReason(interestDate.getReason())
            .interestEndDateType(CCDInterestEndDateType.valueOf(interestDate.getEndDateType().name()));
    }

    @Override
    public InterestDate from(CCDCase ccdCase) {
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
