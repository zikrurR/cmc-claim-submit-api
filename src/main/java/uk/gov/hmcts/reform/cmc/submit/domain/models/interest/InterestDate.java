package uk.gov.hmcts.reform.cmc.submit.domain.models.interest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InterestDate {
    public enum InterestDateType {
        @JsonProperty("custom")
        CUSTOM,

        @JsonProperty("submission")
        SUBMISSION
    }

    public enum InterestEndDateType {
        @JsonProperty("settled_or_judgment")
        SETTLED_OR_JUDGMENT,

        @JsonProperty("submission")
        SUBMISSION
    }


    private InterestDateType type;

    @JsonUnwrapped
    private LocalDate date;

    private String reason;

    private InterestEndDateType endDateType;

}
