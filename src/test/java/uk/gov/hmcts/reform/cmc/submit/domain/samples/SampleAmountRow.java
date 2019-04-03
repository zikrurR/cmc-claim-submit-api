package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;

import java.math.BigDecimal;

public class SampleAmountRow {

    private SampleAmountRow() {
        super();
    }

    public static AmountRow validDefaults() {
        AmountRow amountRow = new AmountRow();
        amountRow.setId("359fda9d-e5fd-4d6e-9525-238642d0157d");
        amountRow.setReason("reason");
        amountRow.setAmount(new BigDecimal("40"));

        return amountRow;
    }
}
