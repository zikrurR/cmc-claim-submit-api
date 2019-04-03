package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.constraints.Money;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InterestBreakdown {
    @NotNull
    @Money
    @DecimalMin(value = "0.00")
    private BigDecimal totalAmount;

    @NotBlank
    private String explanation;

}
