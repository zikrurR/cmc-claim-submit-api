package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdAddressBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;

@Component
public class AddressBuilderConverter {

    public CcdAddressBuilder to(Address address) {
        if (address == null) {
            return null;
        }

        CcdAddressBuilder ccdAddressBuilder = CcdAddressBuilder.builder();
        ccdAddressBuilder.addressLine1(address.getLine1());
        ccdAddressBuilder.addressLine2(address.getLine2());
        ccdAddressBuilder.addressLine3(address.getLine3());
        ccdAddressBuilder.postTown(address.getCity());
        ccdAddressBuilder.postCode(address.getPostcode());

        return ccdAddressBuilder;
    }

}
