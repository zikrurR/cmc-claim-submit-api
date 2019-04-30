package uk.gov.hmcts.reform.cmc.submit.merger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class MergeCaseDataTest {
    @Spy
    private MergeCaseDataApplicants applicantsDecorator;
    @Spy
    private MergeCaseDataRespondents respondentsDecorator;
    @Spy
    private MergeCaseDataAmount amountDecorator;
    @Spy
    private MergeCaseDataPayment paymentDecorator;
    @Spy
    private MergeCaseDataInterest interestDecorator;
    @Spy
    private MergeCaseDataTimeline timelineDecorator;
    @Spy
    private MergeCaseDataEvidence evidenceDecorator;

    @InjectMocks
    private MergeCaseData mergeCaseData;

    @BeforeEach
    public void setup() {


        AddressBuilderConverter addressMapper = new AddressBuilderConverter();

        applicantsDecorator = new MergeCaseDataApplicants(addressMapper);
        respondentsDecorator = new MergeCaseDataRespondents(addressMapper);
        amountDecorator = new MergeCaseDataAmount();
        paymentDecorator = new MergeCaseDataPayment();
        interestDecorator = new MergeCaseDataInterest();
        timelineDecorator = new MergeCaseDataTimeline();
        evidenceDecorator = new MergeCaseDataEvidence();

        mergeCaseData = new MergeCaseData(new ObjectMapper());

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldMapClaimSimpleImputFromMapModel() {


        Map<String, Object> initData = new HashMap<>();

        ClaimInput claim = SampleClaimImput.validDefaults();

        Map<String, Object> merge = mergeCaseData.merge(initData, claim);

        assertThat(merge.get("reason")).isEqualTo("reason");
    }

}