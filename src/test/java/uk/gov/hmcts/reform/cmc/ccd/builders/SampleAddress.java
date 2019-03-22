package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.cmc.domain.models.common.Address;

public class SampleAddress {

    private SampleAddress() {
        super();
    }

    public static Address validDefaults() {
        Address address = new Address();
        address.setLine1("52");
        address.setLine2("Down Street");
        address.setLine3("Salford");
        address.setCity("Manchester");
        address.setPostcode("DF1 3LJ");

        return address;
    }

    public static Address invalidDefaults() {
        Address address = new Address();
        address.setLine1("52");
        address.setLine2("Down Street");
        address.setLine3("Salford");
        address.setCity("Manchester");
        address.setPostcode("");

        return address;
    }
}
