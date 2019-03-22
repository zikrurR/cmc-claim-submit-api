package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CCDAddress;
import uk.gov.hmcts.cmc.domain.models.common.Address;

import java.util.Objects;

public class CcdAddressAssert extends AbstractAssert<CcdAddressAssert, CCDAddress> {

    public CcdAddressAssert(CCDAddress actual) {
        super(actual, CcdAddressAssert.class);
    }

    public CcdAddressAssert isEqualTo(Address address) {

        if (actual == null && address == null) return this;

        if (actual == null) {
            failWithMessage("Expected CCDAddress to be not null");
        }

        if (!Objects.equals(actual.getAddressLine1(), address.getLine1())) {
            failWithMessage("Expected CCDAddress.line1 to be <%s> but was <%s>",
                address.getLine1(), actual.getAddressLine1());
        }

        if (!Objects.equals(actual.getAddressLine2(), address.getLine2())) {
            failWithMessage("Expected CCDAddress.line2 to be <%s> but was <%s>",
                address.getLine2(), actual.getAddressLine2());
        }

        if (!Objects.equals(actual.getAddressLine3(), address.getLine3())) {
            failWithMessage("Expected CCDAddress.line3 to be <%s> but was <%s>",
                address.getLine3(), actual.getAddressLine3());
        }

        if (!Objects.equals(actual.getPostTown(), address.getCity())) {
            failWithMessage("Expected CCDAddress.city to be <%s> but was <%s>",
                address.getCity(), actual.getPostTown());
        }

        if (!Objects.equals(actual.getPostCode(), address.getPostcode())) {
            failWithMessage("Expected CCDAddress.postcode to be <%s> but was <%s>",
                address.getPostcode(), actual.getPostCode());
        }

        return this;
    }

}
