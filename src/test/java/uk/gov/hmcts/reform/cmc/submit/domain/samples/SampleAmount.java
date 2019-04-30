package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;

import java.math.BigDecimal;

import static java.util.Collections.singletonList;

public class SampleAmount {

    private SampleAmount() {
        super();
    }

    public static AmountBreakDown validDefaults() {
        return validAmountBreakDown();
    }

    public static NotKnown validNotKnown() {
        NotKnown notKnow = new NotKnown();
        return notKnow;
    }

    public static AmountBreakDown validAmountBreakDown() {
        AmountRow amountRow = new AmountRow();
        amountRow.setId("359fda9d-e5fd-4d6e-9525-238642d0157d");
        amountRow.setReason("reason");
        amountRow.setAmount(new BigDecimal("40"));

        AmountBreakDown amountBreakDown = new AmountBreakDown();
        amountBreakDown.setRows(singletonList(amountRow));
        return amountBreakDown;
    }

    public static AmountRange validAmountRange() {
        AmountRange amountRange = new AmountRange();
        amountRange.setLowerValue(BigDecimal.valueOf(100L));
        amountRange.setHigherValue(BigDecimal.valueOf(99000L));

        return amountRange;

    }
}
