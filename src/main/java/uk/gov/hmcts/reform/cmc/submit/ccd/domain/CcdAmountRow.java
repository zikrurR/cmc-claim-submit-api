package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CcdAmountRow {
    private String reason;
    private BigDecimal amount;
}
