package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

@Data
public class Interest {
    public enum InterestType {
        @JsonProperty("standard")
        STANDARD,
        @JsonProperty("different")
        DIFFERENT,
        @JsonProperty("breakdown")
        BREAKDOWN,
        @JsonProperty("no interest")
        NO_INTEREST
    }

    @NotNull
    private InterestType type;

    private InterestBreakdown interestBreakdown;

    private BigDecimal rate;

    private String reason;

    private BigDecimal specificDailyAmount;

    private InterestDate interestDate;

}
