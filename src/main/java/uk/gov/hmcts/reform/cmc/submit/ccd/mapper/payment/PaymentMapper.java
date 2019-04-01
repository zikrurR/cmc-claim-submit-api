package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.payment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.domain.models.payment.AccountPayment;
import uk.gov.hmcts.cmc.domain.models.payment.Payment;
import uk.gov.hmcts.cmc.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.LocalDateTimeFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class PaymentMapper implements BuilderMapper<CcdCase, Payment, CcdCase.CcdCaseBuilder> {

    @Override
    public void to(Payment payment, CcdCase.CcdCaseBuilder builder) {
        if (payment == null) {
            return;
        }

        if (payment instanceof ReferencePayment){
            toReferencePayment((ReferencePayment)payment, builder);
        } else if (payment instanceof AccountPayment){
            toAccountPayment((AccountPayment)payment, builder);
        }

    }

    private void toReferencePayment(ReferencePayment payment, CcdCase.CcdCaseBuilder builder) {

        builder.paymentAmount(payment.getAmount());
        builder.paymentId(payment.getId());
        builder.paymentReference(payment.getReference());
        builder.paymentStatus(payment.getStatus());

        if (StringUtils.isNotBlank(payment.getDateCreated())) {
            builder.paymentDateCreated(parseDate(payment.getDateCreated()));
        }
    }

    private void toAccountPayment(AccountPayment payment, CcdCase.CcdCaseBuilder builder) {
        builder.feeAccountNumber(payment.getFeeAccountNumber());
    }

    @Override
    public Payment from(CcdCase ccdCase) {

        if (!isBlank(ccdCase.getFeeAccountNumber())) {
            AccountPayment payment = new AccountPayment();
            payment.setFeeAccountNumber(ccdCase.getFeeAccountNumber());

            return payment;
        } else if (!(isBlank(ccdCase.getPaymentId())
            && ccdCase.getPaymentAmount() == null
            && isBlank(ccdCase.getPaymentReference())
            && ccdCase.getPaymentDateCreated() == null
            && isBlank(ccdCase.getPaymentStatus())
        )) {

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

    private LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, ISO_DATE);
        } catch (DateTimeParseException e) {
            return LocalDateTimeFactory.fromLong(Long.valueOf(input));
        }
    }
}
