package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.InterestBreakdown;

import java.math.BigDecimal;

public class SampleInterestBreakdown {

    private BigDecimal totalAmount = new BigDecimal("40.00");
    private String explanation = "I've given this amount because...";

    public static InterestBreakdown validDefaults() {
        return builder().build();
    }

    public static SampleInterestBreakdown builder() {
        return new SampleInterestBreakdown();
    }

    public InterestBreakdown build() {

        InterestBreakdown interestBreakdown = new InterestBreakdown();
        interestBreakdown.setExplanation(explanation);
        interestBreakdown.setTotalAmount(totalAmount);

        return interestBreakdown;
    }

    public SampleInterestBreakdown withTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public SampleInterestBreakdown withExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

}
