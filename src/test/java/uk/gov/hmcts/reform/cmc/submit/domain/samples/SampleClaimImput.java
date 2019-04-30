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
        claimData.setAmount(SampleAmount.validAmountBreakDown());
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

    public static ClaimInput validOrganisationClaim() {

        ClaimInput claimData = new ClaimInput();

        claimData.setExternalId(UUID.randomUUID());
        claimData.setClaimants(singletonList(SampleParty.organisation()));
        claimData.setDefendants(singletonList(SampleTheirDetails.organisationDetails()));
        claimData.setPayment(SamplePayment.validAccountPayment());
        claimData.setAmount(SampleAmount.validAmountRange());
        claimData.setInterest(SampleInterest.breakdownOnly());
        claimData.setPersonalInjury(SamplePersonalInjury.validDefaults());
        claimData.setHousingDisrepair(SampleHousingDisrepair.validDefaults());
        claimData.setReason("reason");
        claimData.setStatementOfTruth(SampleStatementOfTruth.validDefaults());
        claimData.setPreferredCourt("LONDON COUNTY COUNCIL");
        claimData.setTimeline(Arrays.asList(SampleTimelineEvent.validDefaults()));
        claimData.setEvidences(Arrays.asList(SampleEvidence.validDefaults()));

        return claimData;
    }

    public static ClaimInput validCompanyClaim() {

        ClaimInput claimData = new ClaimInput();

        claimData.setExternalId(UUID.randomUUID());
        claimData.setClaimants(singletonList(SampleParty.company()));
        claimData.setDefendants(singletonList(SampleTheirDetails.companyDetails()));
        claimData.setPayment(SamplePayment.validAccountPayment());
        claimData.setAmount(SampleAmount.validNotKnown());
        claimData.setInterest(SampleInterest.noInterest());
        claimData.setPersonalInjury(SamplePersonalInjury.validDefaults());
        claimData.setHousingDisrepair(SampleHousingDisrepair.validDefaults());
        claimData.setReason("reason");
        claimData.setStatementOfTruth(SampleStatementOfTruth.validDefaults());
        claimData.setPreferredCourt("LONDON COUNTY COUNCIL");
        claimData.setTimeline(Arrays.asList(SampleTimelineEvent.validDefaults()));
        claimData.setEvidences(Arrays.asList(SampleEvidence.validDefaults()));

        return claimData;
    }

    public static ClaimInput validSoleTraderClaim() {

        ClaimInput claimData = new ClaimInput();

        claimData.setExternalId(UUID.randomUUID());
        claimData.setClaimants(singletonList(SampleParty.soleTrader()));
        claimData.setDefendants(singletonList(SampleTheirDetails.soleTraderDetails()));
        claimData.setPayment(SamplePayment.validAccountPayment());
        claimData.setAmount(SampleAmount.validAmountBreakDown());
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
