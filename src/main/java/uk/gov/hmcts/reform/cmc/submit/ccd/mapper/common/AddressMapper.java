package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDAddress;
import uk.gov.hmcts.cmc.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;

@Component
public class AddressMapper implements Mapper<CCDAddress, Address> {

    @Override
    public CCDAddress to(Address address) {
        if (address == null) return null;

        return CCDAddress
            .builder()
            .addressLine1(address.getLine1())
            .addressLine2(address.getLine2())
            .addressLine3(address.getLine3())
            .postTown(address.getCity())
            .postCode(address.getPostcode())
            .build();
    }

    @Override
    public Address from(CCDAddress ccdAddress) {
        if (ccdAddress == null) return null;

        Address address = new Address();
        address.setLine1(ccdAddress.getAddressLine1());
        address.setLine2(ccdAddress.getAddressLine2());
        address.setLine3(ccdAddress.getAddressLine3());
        address.setPostcode(ccdAddress.getPostCode());
        address.setCity(ccdAddress.getPostTown());

        return address;
    }
}
