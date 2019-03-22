package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.interest.InterestBreakdown;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

@Component
public class InterestBreakdownMapper implements BuilderMapper<CCDCase, InterestBreakdown, CCDCase.CCDCaseBuilder> {
    @Override
    public void to(InterestBreakdown interestBreakdown, CCDCase.CCDCaseBuilder builder) {
        if (interestBreakdown == null) {
            return;
        }

        builder
            .interestBreakDownAmount(interestBreakdown.getTotalAmount())
            .interestBreakDownExplanation(interestBreakdown.getExplanation());
    }

    @Override
    public InterestBreakdown from(CCDCase ccdCase) {
        if (ccdCase.getInterestBreakDownAmount() == null && ccdCase.getInterestBreakDownExplanation() == null) {
            return null;
        }

        InterestBreakdown interestBreakdown = new InterestBreakdown();

        interestBreakdown.setExplanation(ccdCase.getInterestBreakDownExplanation());
        interestBreakdown.setTotalAmount(ccdCase.getInterestBreakDownAmount());

        return interestBreakdown;
    }
}
