package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class PaymentConverter {

    public Payment from(CcdCase ccdCase) {
        Payment payment;

        if (isBlank(ccdCase.getFeeAccountNumber())) {
            payment = referencePayment(ccdCase);
        } else {
            payment = accountPayment(ccdCase);
        }
        return payment;
    }

    private AccountPayment accountPayment(CcdCase ccdCase) {
        AccountPayment payment = new AccountPayment();
        payment.setFeeAccountNumber(ccdCase.getFeeAccountNumber());
        return payment;
    }

    private ReferencePayment referencePayment(CcdCase ccdCase) {
        ReferencePayment payment = new ReferencePayment();

        payment.setId(ccdCase.getPaymentId());
        payment.setAmount(ccdCase.getPaymentAmount());
        payment.setReference(ccdCase.getPaymentReference());
        payment.setDateCreated(ccdCase.getPaymentDateCreated());
        payment.setStatus(ccdCase.getPaymentStatus());
        return payment;
    }

}
