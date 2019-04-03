package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;

import java.math.BigDecimal;

public class SampleAmountRange {

    private SampleAmountRange() {
        super();
    }

    public static AmountRange validDefaults() {
        AmountRange amountRange = new AmountRange();
        amountRange.setLowerValue(BigDecimal.valueOf(100L));
        amountRange.setHigherValue(BigDecimal.valueOf(99000L));

        return amountRange;

    }
}
