package uk.gov.hmcts.reform.cmc.submit.merger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.LocalDateTimeFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ISO_DATE;

@Component
class MergeCaseDataPayment implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        Payment payment = claim.getPayment();
        if (payment == null) {
            return;
        }

        if (payment instanceof ReferencePayment){
            toReferencePayment((ReferencePayment)payment, ccdCase);
        } else if (payment instanceof AccountPayment){
            toAccountPayment((AccountPayment)payment, ccdCase);
        }

    }

    private void toReferencePayment(ReferencePayment payment, CcdCase ccdCase) {

        ccdCase.setPaymentAmount(payment.getAmount());
        ccdCase.setPaymentId(payment.getId());
        ccdCase.setPaymentReference(payment.getReference());
        ccdCase.setPaymentStatus(payment.getStatus());

        if (StringUtils.isNotBlank(payment.getDateCreated())) {
            ccdCase.setPaymentDateCreated(parseDate(payment.getDateCreated()));
        }
    }

    private void toAccountPayment(AccountPayment payment, CcdCase builder) {
        builder.setFeeAccountNumber(payment.getFeeAccountNumber());
    }

    private LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, ISO_DATE);
        } catch (DateTimeParseException e) {
            return LocalDateTimeFactory.fromLong(Long.valueOf(input));
        }
    }



}
