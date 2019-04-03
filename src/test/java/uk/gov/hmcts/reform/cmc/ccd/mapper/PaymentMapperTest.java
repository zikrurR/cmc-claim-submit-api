package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.ccd.builders.SamplePayment;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.payment.PaymentMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.submit.domain.utils.LocalDateTimeFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMapperTest {

    private PaymentMapper mapper = new PaymentMapper();

    @Test
    public void shouldMapPaymentToCcd() {
        //given
        ReferencePayment payment = SamplePayment.validDefaults();

        //when
        CcdCase.CcdCaseBuilder caseBuilder = CcdCase.builder();
        mapper.to(payment, caseBuilder);
        CcdCase ccdCase = caseBuilder.build();

        //then
        assertThat(LocalDate.parse(payment.getDateCreated(), ISO_DATE))
            .isEqualTo(ccdCase.getPaymentDateCreated());
        assertThat(payment.getId()).isEqualTo(ccdCase.getPaymentId());
        assertThat(payment.getAmount()).isEqualTo(ccdCase.getPaymentAmount());
        assertThat(payment.getReference()).isEqualTo(ccdCase.getPaymentReference());
        assertThat(payment.getStatus()).isEqualTo(ccdCase.getPaymentStatus());
    }

    @Test
    public void shouldMapPaymentToCcdWhenNoCreatedDateProvided() {
        //given
        ReferencePayment payment = SamplePayment.validDefaults();
        payment.setDateCreated(null);

        //when
        CcdCase.CcdCaseBuilder caseBuilder = CcdCase.builder();
        mapper.to(payment, caseBuilder);
        CcdCase ccdCase = caseBuilder.build();

        //then
        assertThat(ccdCase.getPaymentDateCreated()).isNull();
        assertThat(payment.getId()).isEqualTo(ccdCase.getPaymentId());
        assertThat(payment.getAmount()).isEqualTo(ccdCase.getPaymentAmount());
        assertThat(payment.getReference()).isEqualTo(ccdCase.getPaymentReference());
        assertThat(payment.getStatus()).isEqualTo(ccdCase.getPaymentStatus());
    }

    @Test
    public void shouldMapPaymentToCcdWhenLongCreatedDateProvided() {
        //given
        ReferencePayment payment = SamplePayment.validDefaults();
        payment.setDateCreated("1511169381890");

        //when
        CcdCase.CcdCaseBuilder caseBuilder = CcdCase.builder();
        mapper.to(payment, caseBuilder);
        CcdCase ccdCase = caseBuilder.build();

        //then
        assertThat(LocalDateTimeFactory.fromLong(Long.valueOf(payment.getDateCreated())))
            .isEqualTo(ccdCase.getPaymentDateCreated());
        assertThat(payment.getId()).isEqualTo(ccdCase.getPaymentId());
        assertThat(payment.getAmount()).isEqualTo(ccdCase.getPaymentAmount());
        assertThat(payment.getReference()).isEqualTo(ccdCase.getPaymentReference());
        assertThat(payment.getStatus()).isEqualTo(ccdCase.getPaymentStatus());
    }

    @Test
    public void shouldMapPaymentFromCcd() {
        //given
        CcdCase ccdCase = CcdCase.builder()
            .paymentAmount(BigDecimal.valueOf(4000))
            .paymentReference("RC-1524-6488-1670-7520")
            .paymentId("PaymentId")
            .paymentStatus("success")
            .paymentDateCreated(LocalDate.of(2019, 01, 01))
            .build();

        //when
        ReferencePayment payment = (ReferencePayment)mapper.from(ccdCase);

        //then
        assertThat(LocalDate.parse(payment.getDateCreated(), ISO_DATE))
            .isEqualTo(ccdCase.getPaymentDateCreated());
        assertThat(payment.getId()).isEqualTo(ccdCase.getPaymentId());
        assertThat(payment.getAmount()).isEqualTo(ccdCase.getPaymentAmount());
        assertThat(payment.getReference()).isEqualTo(ccdCase.getPaymentReference());
        assertThat(payment.getStatus()).isEqualTo(ccdCase.getPaymentStatus());
    }

    @Test
    public void shouldMapPaymentFromCcdWhenNoDateCreatedProvided() {
        //given
        CcdCase ccdCase = CcdCase.builder()
            .paymentAmount(BigDecimal.valueOf(4000))
            .paymentReference("RC-1524-6488-1670-7520")
            .paymentId("PaymentId")
            .paymentStatus("success")
            .paymentDateCreated(null)
            .build();

        //when
        ReferencePayment payment = (ReferencePayment)mapper.from(ccdCase);

        //then
        assertThat(payment.getDateCreated()).isBlank();
        assertThat(payment.getId()).isEqualTo(ccdCase.getPaymentId());
        assertThat(payment.getAmount()).isEqualTo(ccdCase.getPaymentAmount());
        assertThat(payment.getReference()).isEqualTo(ccdCase.getPaymentReference());
        assertThat(payment.getStatus()).isEqualTo(ccdCase.getPaymentStatus());
    }
}
