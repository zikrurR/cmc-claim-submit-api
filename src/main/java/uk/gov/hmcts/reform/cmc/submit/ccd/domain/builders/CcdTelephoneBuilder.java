package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTelephone;

import java.util.HashMap;
import java.util.Map;

public class CcdTelephoneBuilder implements Builder<CcdTelephone> {
    private String telephoneNumber;
    private String telephoneUsageType;
    private String contactDirection;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdTelephoneBuilder builder() {
        return new CcdTelephoneBuilder();
    }

    private CcdTelephoneBuilder() {
    }

    @Override
    public CcdTelephone build() {
        return new CcdTelephone(telephoneNumber, telephoneUsageType, contactDirection);
    }

    public Map<String, Object> buildMap() {
        return propertiesMap;
    }

    public CcdTelephoneBuilder telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        propertiesMap.put("telephoneNumber", telephoneNumber);
        return this;
    }

    public CcdTelephoneBuilder telephoneUsageType(String telephoneUsageType) {
        this.telephoneUsageType = telephoneUsageType;
        propertiesMap.put("telephoneUsageType", telephoneUsageType);
        return this;
    }

    public CcdTelephoneBuilder contactDirection(String contactDirection) {
        this.contactDirection = contactDirection;
        propertiesMap.put("contactDirection", contactDirection);
        return this;
    }
}