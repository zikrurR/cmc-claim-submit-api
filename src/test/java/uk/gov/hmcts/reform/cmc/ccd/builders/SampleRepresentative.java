package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.cmc.domain.models.Representative;
import uk.gov.hmcts.cmc.domain.models.Representative.RepresentativeBuilder;

public class SampleRepresentative {

    private SampleRepresentative() {
        super();
    }

    public static RepresentativeBuilder builder() {
        return Representative.builder()
            .organisationName("Trading ltd")
            .organisationAddress(SampleAddress.builder().build())
            .organisationContactDetails(SampleContactDetails.builder().build());
    }
}
