package uk.gov.hmcts.reform.cmc.ccd.assertion;

import uk.gov.hmcts.cmc.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

public class Assertions {

    private Assertions() {
    }

    public static AddressAssert assertThat(Address address) {
        return new AddressAssert(address);
    }

    public static CcdAddressAssert assertThat(CcdAddress ccdAddress) {
        return new CcdAddressAssert(ccdAddress);
    }

    public static TimelineEventAssert assertThat(TimelineEvent timelineEvent) {
        return new TimelineEventAssert(timelineEvent);
    }

    public static EvidenceAssert assertThat(Evidence evidence) {
        return new EvidenceAssert(evidence);
    }

    public static ClaimAssert assertThat(ClaimData claim) {
        return new ClaimAssert(claim);
    }

    public static ClaimantAssert assertThat(Party party) {
        return new ClaimantAssert(party);
    }

    public static TheirDetailsAssert assertThat(TheirDetails theirDetails) {
        return new TheirDetailsAssert(theirDetails);
    }

}
