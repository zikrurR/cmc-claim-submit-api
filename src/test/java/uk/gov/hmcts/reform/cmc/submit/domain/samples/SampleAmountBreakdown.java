package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;

import java.math.BigDecimal;

import static java.util.Collections.singletonList;

public class SampleAmountBreakdown {

    private SampleAmountBreakdown() {
        super();
    }

    public static AmountBreakDown validDefaults() {
        AmountRow amountRow = new AmountRow();
        amountRow.setId("359fda9d-e5fd-4d6e-9525-238642d0157d");
        amountRow.setReason("reason");
        amountRow.setAmount(new BigDecimal("40"));

        AmountBreakDown amountBreakDown = new AmountBreakDown();
        amountBreakDown.setRows(singletonList(amountRow));
        return amountBreakDown;
    }
}
