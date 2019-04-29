package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;

import java.util.HashMap;
import java.util.Map;


public class CcdAddressBuilder implements Builder<CcdAddress> {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postTown;
    private String postCode;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdAddressBuilder builder() {
        return new CcdAddressBuilder();
    }

    private CcdAddressBuilder() {
    }

    @Override
    public CcdAddress build() {
        CcdAddress ccdAddress = new CcdAddress();
        ccdAddress.setAddressLine1(addressLine1);
        ccdAddress.setAddressLine2(addressLine2);
        ccdAddress.setAddressLine3(addressLine3);
        ccdAddress.setPostCode(postCode);
        ccdAddress.setPostTown(postTown);

        return ccdAddress;
    }

    public Map<String, Object> buildMap() {
        HashMap<String, Object> hashMap = new HashMap<>(propertiesMap);
        return hashMap;
    }

    public CcdAddressBuilder addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        propertiesMap.put("AddressLine1", addressLine1);
        return this;
    }

    public CcdAddressBuilder addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        propertiesMap.put("AddressLine2", addressLine2);
        return this;
    }

    public CcdAddressBuilder addressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        propertiesMap.put("AddressLine3", addressLine3);
        return this;
    }

    public CcdAddressBuilder postTown(String postTown) {
        this.postTown = postTown;
        propertiesMap.put("PostTown", postTown);
        return this;
    }

    public CcdAddressBuilder postCode(String postCode) {
        this.postCode = postCode;
        propertiesMap.put("PostCode", postCode);
        return this;
    }
}