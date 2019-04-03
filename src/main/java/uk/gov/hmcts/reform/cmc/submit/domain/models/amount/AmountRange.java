package uk.gov.hmcts.reform.cmc.submit.domain.models.amount;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.constraints.Money;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class AmountRange implements Amount {

    @Money
    @DecimalMin(value = "0.01")
    private BigDecimal lowerValue;

    @NotNull
    @Money
    @DecimalMin(value = "0.01")
    private BigDecimal higherValue;


}
