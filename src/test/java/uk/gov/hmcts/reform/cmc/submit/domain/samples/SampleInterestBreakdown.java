package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;

import java.math.BigDecimal;

public class SampleInterestBreakdown {

    private SampleInterestBreakdown() {
        super();
    }

    public static InterestBreakdown validDefaults() {

        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation("I've given this amount because...");
        interestBreakdown.setTotalAmount(new BigDecimal("40.00"));

        return interestBreakdown;
    }

}
