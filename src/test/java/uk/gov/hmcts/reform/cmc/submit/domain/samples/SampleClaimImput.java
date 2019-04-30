package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

import java.util.Arrays;
import java.util.UUID;

import static java.util.Collections.singletonList;

public class SampleClaimImput {

    private SampleClaimImput() {
        super();
    }

    public static ClaimInput validDefaults() {

        ClaimInput claimData = new ClaimInput();

        claimData.setExternalId(UUID.randomUUID());
        claimData.setClaimants(singletonList(SampleParty.individual()));
        claimData.setDefendants(singletonList(SampleTheirDetails.individualDetails()));
        claimData.setPayment(SamplePayment.validDefaults());
        claimData.setAmount(SampleAmountBreakdown.validDefaults());
        claimData.setInterest(SampleInterest.standard());
        claimData.setPersonalInjury(SamplePersonalInjury.validDefaults());
        claimData.setHousingDisrepair(SampleHousingDisrepair.validDefaults());
        claimData.setReason("reason");
        claimData.setStatementOfTruth(SampleStatementOfTruth.validDefaults());
        claimData.setPreferredCourt("LONDON COUNTY COUNCIL");
        claimData.setTimeline(Arrays.asList(SampleTimelineEvent.validDefaults()));
        claimData.setEvidences(Arrays.asList(SampleEvidence.validDefaults()));

        return claimData;
    }

}
