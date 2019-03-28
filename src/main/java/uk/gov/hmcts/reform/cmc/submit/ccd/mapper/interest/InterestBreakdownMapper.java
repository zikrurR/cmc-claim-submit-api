package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.domain.models.interest.InterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

@Component
public class InterestBreakdownMapper implements BuilderMapper<CcdCase, InterestBreakdown, CcdCase.CcdCaseBuilder> {
    @Override
    public void to(InterestBreakdown interestBreakdown, CcdCase.CcdCaseBuilder builder) {
        if (interestBreakdown == null) {
            return;
        }

        builder
            .interestBreakDownAmount(interestBreakdown.getTotalAmount())
            .interestBreakDownExplanation(interestBreakdown.getExplanation());
    }

    @Override
    public InterestBreakdown from(CcdCase ccdCase) {
        if (ccdCase.getInterestBreakDownAmount() == null && ccdCase.getInterestBreakDownExplanation() == null) {
            return null;
        }

        InterestBreakdown interestBreakdown = new InterestBreakdown();

        interestBreakdown.setExplanation(ccdCase.getInterestBreakDownExplanation());
        interestBreakdown.setTotalAmount(ccdCase.getInterestBreakDownAmount());

        return interestBreakdown;
    }
}
