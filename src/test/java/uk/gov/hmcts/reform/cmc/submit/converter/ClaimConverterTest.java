package uk.gov.hmcts.reform.cmc.submit.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ClaimConverterTest {


    private ClaimantConverter claimantConverter;
    private DefendantConverter defendantConverter;
    private AmountConverter amountConverter;
    private PaymentConverter paymentConverter;
    private InterestConverter interestConverter;
    private TimelineConverter timelineConverter;
    private EvidenceConverter evidenceConverter;

    private ClaimConverter claimConverter;

    @BeforeEach
    public void setup() {

        CommonAddressConverter addressMapper = new CommonAddressConverter();

        claimantConverter = new ClaimantConverter(addressMapper);
        defendantConverter = new DefendantConverter(addressMapper);
        amountConverter = new AmountConverter();
        paymentConverter = new PaymentConverter();
        interestConverter = new InterestConverter();
        timelineConverter = new TimelineConverter();
        evidenceConverter = new EvidenceConverter();

        claimConverter = new ClaimConverter(
                new ObjectMapper(),
                claimantConverter,
                defendantConverter,
                amountConverter,
                paymentConverter,
                interestConverter,
                timelineConverter,
                evidenceConverter);

    }

    @Test
    public void shouldMapClaimSimpleImputFromMapModel() {

        Map<String,Object> mandatoryData = Maps.newHashMap();
        String externalId = UUID.randomUUID().toString();
        mandatoryData.put("evidence", new ArrayList<>());
        mandatoryData.put("amountBreakDown", new ArrayList<>());
        mandatoryData.put("timeline", new ArrayList<>());
        mandatoryData.put("respondents", new ArrayList<>());
        mandatoryData.put("applicants", new ArrayList<>());

        mandatoryData.put("referenceNumber", "random_reference_number");
        mandatoryData.put("externalId", externalId);
        mandatoryData.put("reason", "raison");
        mandatoryData.put("amountType", "RANGE");
        Claim convert = claimConverter.convert(mandatoryData);

        assertThat(convert.getReason()).isEqualTo("raison");
        assertThat(convert.getExternalId().toString()).isEqualTo(externalId);
    }

}
