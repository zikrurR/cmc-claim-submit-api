package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;

@Component
public class CommonAddressConverter {

    public Address from(CcdAddress ccdAddress) {
        if (ccdAddress == null) {
            return null;
        }

        Address address = new Address();
        address.setLine1(ccdAddress.getAddressLine1());
        address.setLine2(ccdAddress.getAddressLine2());
        address.setLine3(ccdAddress.getAddressLine3());
        address.setPostcode(ccdAddress.getPostCode());
        address.setCity(ccdAddress.getPostTown());

        return address;
    }
}
