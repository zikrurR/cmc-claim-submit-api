package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CcdAmountRow {
    private String reason;
    private BigInteger amount;
}
