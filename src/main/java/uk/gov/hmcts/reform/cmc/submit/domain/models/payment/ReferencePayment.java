package uk.gov.hmcts.reform.cmc.submit.domain.models.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReferencePayment implements Payment {

    private String id;

    @NotBlank
    private String reference;

    @NotNull
    private BigDecimal amount;

    @JsonProperty("date_created")
    private String dateCreated;

    private String status;

}
