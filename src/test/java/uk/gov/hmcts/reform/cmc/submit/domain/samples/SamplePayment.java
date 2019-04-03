package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

import java.math.BigDecimal;

public class SamplePayment {

    private SamplePayment() {
        super();
    }

    public static ReferencePayment validDefaults() {
        ReferencePayment payment = new ReferencePayment();
        payment.setReference("RC-1524-6488-1670-7520");
        payment.setAmount(BigDecimal.valueOf(4000));
        payment.setDateCreated("2019-01-01");
        payment.setId("PaymentId");
        payment.setStatus("success");

        return payment;
    }
}
