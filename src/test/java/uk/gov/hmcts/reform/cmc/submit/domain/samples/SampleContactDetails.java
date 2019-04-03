package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;

public class SampleContactDetails {

    private SampleContactDetails() {
        super();
    }

    public static ContactDetails validDefaults() {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setPhone("7873738547");
        contactDetails.setEmail("representative@example.org");
        contactDetails.setDxAddress("DX123456");

        return contactDetails;
    }
}
