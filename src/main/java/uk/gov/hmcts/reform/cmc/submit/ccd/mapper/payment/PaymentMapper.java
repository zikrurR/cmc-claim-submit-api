package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.payment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.payment.AccountPayment;
import uk.gov.hmcts.cmc.domain.models.payment.Payment;
import uk.gov.hmcts.cmc.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.domain.utils.LocalDateTimeFactory;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class PaymentMapper implements BuilderMapper<CCDCase, Payment, CCDCase.CCDCaseBuilder> {

    @Override
    public void to(Payment payment, CCDCase.CCDCaseBuilder builder) {
        if (payment == null) {
            return;
        }

        if (payment.getClass().isInstance(ReferencePayment.class)){
            toReferencePayment((ReferencePayment)payment, builder);
        } else if (payment.getClass().isInstance(AccountPayment.class)){
            toAccountPayment((AccountPayment)payment, builder);
        }

    }

    private void toReferencePayment(ReferencePayment payment, CCDCase.CCDCaseBuilder builder) {
        builder
            .paymentAmount(payment.getAmount())
            .paymentId(payment.getId())
            .paymentReference(payment.getReference())
            .paymentStatus(payment.getStatus());

        if (StringUtils.isNotBlank(payment.getDateCreated())) {
            builder.paymentDateCreated(parseDate(payment.getDateCreated()));
        }
    }

    private void toAccountPayment(AccountPayment payment, CCDCase.CCDCaseBuilder builder) {
        builder.feeAccountNumber(payment.getFeeAccountNumber());
    }

    @Override
    public Payment from(CCDCase ccdCase) {

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
