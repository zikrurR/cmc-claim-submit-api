package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class CcdAmountRowBuilder implements Builder<CcdAmountRow> {
    private String reason;
    private BigDecimal amount;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdAmountRowBuilder builder() {
        return new CcdAmountRowBuilder();
    }

    private CcdAmountRowBuilder() {
    }

    @Override
    public CcdAmountRow build() {

        CcdAmountRow ccdAmountRow = new CcdAmountRow();
        ccdAmountRow.setReason(reason);
        ccdAmountRow.setAmount(amount);
        return ccdAmountRow;
    }

    public Map<String, Object> buildMap() {
        return new HashMap<>(propertiesMap);
    }

    public CcdAmountRowBuilder reason(String reason) {
        this.reason = reason;
        propertiesMap.put("reason", reason);
        return this;
    }

    public CcdAmountRowBuilder amount(BigDecimal amount) {
        this.amount = amount;
        propertiesMap.put("amount", amount);
        return this;
    }

}
