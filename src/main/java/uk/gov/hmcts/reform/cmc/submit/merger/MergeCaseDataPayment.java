package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

@Component
class MergeCaseDataPayment implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        Payment payment = claim.getPayment();
        if (payment instanceof ReferencePayment) {
            toReferencePayment((ReferencePayment)payment, ccdCaseBuilder);
        } else if (payment instanceof AccountPayment) {
            toAccountPayment((AccountPayment)payment, ccdCaseBuilder);
        }

    }

    private void toReferencePayment(ReferencePayment payment, CcdCaseBuilder ccdCaseBuilder) {

        ccdCaseBuilder.paymentAmount(payment.getAmount());
        ccdCaseBuilder.paymentId(payment.getId());
        ccdCaseBuilder.paymentReference(payment.getReference());
        ccdCaseBuilder.paymentStatus(payment.getStatus());

        ccdCaseBuilder.paymentDateCreated(payment.getDateCreated());
    }

    private void toAccountPayment(AccountPayment payment, CcdCaseBuilder ccdCaseBuilder) {
        ccdCaseBuilder.feeAccountNumber(payment.getFeeAccountNumber());
    }

}
