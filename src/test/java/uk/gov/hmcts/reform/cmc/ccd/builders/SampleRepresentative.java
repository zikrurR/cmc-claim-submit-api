package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

public class SampleRepresentative {

    private SampleRepresentative() {
        super();
    }

    public static Representative validDefaults() {
        Representative representative = new Representative();
        representative.setOrganisationName("Trading ltd");
        representative.setOrganisationAddress(SampleAddress.validDefaults());
        representative.setOrganisationContactDetails(SampleContactDetails.validDefaults());

        return representative;
    }
}
