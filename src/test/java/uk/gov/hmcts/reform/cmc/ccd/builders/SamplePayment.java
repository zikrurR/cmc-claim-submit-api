package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.cmc.domain.models.payment.Payment;

import java.math.BigDecimal;

public class SamplePayment {

    private SamplePayment() {
        super();
    }

    public static Payment validDefaults() {
        Payment payment = new Payment();
        payment.setReference("RC-1524-6488-1670-7520");
        payment.setAmount(BigDecimal.valueOf(4000));
        payment.setDateCreated("2019-01-01");
        payment.setId("PaymentId");
        payment.setStatus("success");

        return payment;
    }
}
