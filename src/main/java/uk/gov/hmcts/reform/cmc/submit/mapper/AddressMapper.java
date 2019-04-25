package uk.gov.hmcts.reform.cmc.submit.mapper;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;

@Component
public class AddressMapper implements Mapper<CcdAddress, Address> {

    @Override
    public CcdAddress to(Address address) {
        if (address == null) {
            return null;
        }

        CcdAddress ccdAddress = new CcdAddress();
        ccdAddress.setAddressLine1(address.getLine1());
        ccdAddress.setAddressLine2(address.getLine2());
        ccdAddress.setAddressLine3(address.getLine3());
        ccdAddress.setPostTown(address.getCity());
        ccdAddress.setPostCode(address.getPostcode());

        return ccdAddress;
    }

    @Override
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
