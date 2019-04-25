package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class PaymentConverter {

    public Payment from(CcdCase ccdCase) {

        if (!isBlank(ccdCase.getFeeAccountNumber())) {
            AccountPayment payment = new AccountPayment();
            payment.setFeeAccountNumber(ccdCase.getFeeAccountNumber());

            return payment;
        } else if (!(isBlank(ccdCase.getPaymentId())
                    && ccdCase.getPaymentAmount() == null
                    && isBlank(ccdCase.getPaymentReference())
                    && ccdCase.getPaymentDateCreated() == null
                    && isBlank(ccdCase.getPaymentStatus()))) {

            ReferencePayment payment = new ReferencePayment();

            payment.setId(ccdCase.getPaymentId());
            payment.setAmount(ccdCase.getPaymentAmount());
            payment.setReference(ccdCase.getPaymentReference());
            payment.setDateCreated(ccdCase.getPaymentDateCreated() != null ? ccdCase.getPaymentDateCreated().format(ISO_DATE) : null);
            payment.setStatus(ccdCase.getPaymentStatus());

            return payment;
        }

        return null;
    }

}
