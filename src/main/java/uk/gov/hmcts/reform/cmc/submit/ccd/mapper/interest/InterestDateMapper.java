package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestDateType;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestDate.InterestEndDateType;

@Component
public class InterestDateMapper implements BuilderMapper<CcdCase, InterestDate, CcdCase.CcdCaseBuilder> {

    @Override
    public void to(InterestDate interestDate, CcdCase.CcdCaseBuilder builder) {
        if (interestDate == null || interestDate.getType() == null) {
            return;
        }

        builder.interestDateType(CcdInterestDateType.valueOf(interestDate.getType().name()))
            .interestClaimStartDate(interestDate.getDate())
            .interestStartDateReason(interestDate.getReason())
            .interestEndDateType(CcdInterestEndDateType.valueOf(interestDate.getEndDateType().name()));
    }

    @Override
    public InterestDate from(CcdCase ccdCase) {
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
