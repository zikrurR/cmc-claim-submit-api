package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SamplePayment {

    private SamplePayment() {
        super();
    }

    public static ReferencePayment validDefaults() {
        return validReferencePayment();
    }

    public static ReferencePayment validReferencePayment() {
        ReferencePayment payment = new ReferencePayment();
        payment.setReference("RC-1524-6488-1670-7520");
        payment.setAmount(new BigDecimal("40.00"));
        payment.setDateCreated(LocalDate.of(2019, 01, 01));
        payment.setId("PaymentId");
        payment.setStatus("success");

        return payment;
    }

    public static AccountPayment validAccountPayment() {
        AccountPayment payment = new AccountPayment();
        payment.setFeeAccountNumber("PBA12345667");

        return payment;
    }
}
