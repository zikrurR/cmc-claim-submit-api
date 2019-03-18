package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails.ContactDetailsBuilder;

public class SampleContactDetails {

    private SampleContactDetails() {
        super();
    }

    public static ContactDetailsBuilder builder() {
        return ContactDetails.builder()
            .phone("7873738547")
            .email("representative@example.org")
            .dxAddress("DX123456");
    }
}
