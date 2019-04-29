package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class CcdPartyBuilder implements Builder<CcdParty> {

    private String partyId;
    private String idamId;
    private CcdPartyType type;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private CcdAddressBuilder primaryAddress;
    private String emailAddress;
    private CcdTelephoneBuilder telephoneNumber;
    private CcdAddressBuilder correspondenceAddress;
    private String businessName;
    private String contactPerson;
    private String companiesHouseNumber;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdPartyBuilder builder() {
        return new CcdPartyBuilder();
    }

    private CcdPartyBuilder() {
    }

    @Override
    public CcdParty build() {
        return new CcdParty(partyId,
                            idamId,
                            type,
                            title,
                            firstName,
                            lastName,
                            dateOfBirth,
                            primaryAddress.build(),
                            emailAddress,
                            telephoneNumber.build(),
                            correspondenceAddress.build(),
                            businessName,
                            contactPerson,
                            companiesHouseNumber);
    }

    public Map<String, Object> buildMap() {
        HashMap<String, Object> hashMap = new HashMap<>(propertiesMap);
        if (primaryAddress != null) {
            hashMap.put("primaryAddress", primaryAddress.buildMap());
        }
        if (telephoneNumber != null) {
            hashMap.put("telephoneNumber", telephoneNumber.buildMap());
        }
        if (correspondenceAddress != null) {
            hashMap.put("correspondenceAddress", correspondenceAddress.buildMap());
        }
        return hashMap;
    }

    public CcdPartyBuilder partyId(String partyId) {
        this.partyId = partyId;
        propertiesMap.put("partyId", partyId);
        return this;
    }

    public CcdPartyBuilder idamId(String idamId) {
        this.idamId = idamId;
        propertiesMap.put("idamId", idamId);
        return this;
    }

    public CcdPartyBuilder type(CcdPartyType type) {
        this.type = type;
        propertiesMap.put("type", type);
        return this;
    }

    public CcdPartyBuilder title(String title) {
        this.title = title;
        propertiesMap.put("title", title);
        return this;
    }

    public CcdPartyBuilder firstName(String firstName) {
        this.firstName = firstName;
        propertiesMap.put("firstName", firstName);
        return this;
    }

    public CcdPartyBuilder lastName(String lastName) {
        this.lastName = lastName;
        propertiesMap.put("lastName", lastName);
        return this;
    }

    public CcdPartyBuilder dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        propertiesMap.put("dateOfBirth", dateOfBirth);
        return this;
    }

    public CcdPartyBuilder primaryAddress(CcdAddressBuilder primaryAddress) {
        this.primaryAddress = primaryAddress;
        return this;
    }

    public CcdPartyBuilder emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        propertiesMap.put("emailAddress", emailAddress);
        return this;
    }

    public CcdPartyBuilder telephoneNumber(CcdTelephoneBuilder telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public CcdPartyBuilder correspondenceAddress(CcdAddressBuilder correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
        return this;
    }

    public CcdPartyBuilder businessName(String businessName) {
        this.businessName = businessName;
        propertiesMap.put("businessName", businessName);
        return this;
    }

    public CcdPartyBuilder contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        propertiesMap.put("contactPerson", contactPerson);
        return this;
    }

    public CcdPartyBuilder companiesHouseNumber(String companiesHouseNumber) {
        this.companiesHouseNumber = companiesHouseNumber;
        propertiesMap.put("companiesHouseNumber", companiesHouseNumber);
        return this;
    }
}
