package uk.gov.hmcts.reform.cmc.submit.domain.models.payment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountPayment implements Payment {

    @NotBlank
    private String feeAccountNumber;
}
