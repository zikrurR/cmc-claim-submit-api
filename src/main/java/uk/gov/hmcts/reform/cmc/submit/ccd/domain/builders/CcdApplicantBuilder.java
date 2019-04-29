package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;

import java.util.HashMap;
import java.util.Map;

public class CcdApplicantBuilder implements Builder<CcdApplicant> {

    private CcdPartyBuilder partyDetail;
    private String partyName;
    private String representativeOrganisationName;
    private CcdAddressBuilder representativeOrganisationAddress;
    private String representativeOrganisationPhone;
    private String representativeOrganisationEmail;
    private String representativeOrganisationDxAddress;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdApplicantBuilder builder() {
        return new CcdApplicantBuilder();
    }

    private CcdApplicantBuilder() {
    }

    @Override
    public CcdApplicant build() {

        CcdApplicant ccdApplicant = new CcdApplicant();
        ccdApplicant.setPartyDetail(partyDetail.build());
        ccdApplicant.setPartyName(partyName);
        ccdApplicant.setRepresentativeOrganisationName(representativeOrganisationName);
        ccdApplicant.setRepresentativeOrganisationAddress(representativeOrganisationAddress.build());
        ccdApplicant.setRepresentativeOrganisationPhone(representativeOrganisationPhone);
        ccdApplicant.setRepresentativeOrganisationEmail(representativeOrganisationEmail);
        ccdApplicant.setRepresentativeOrganisationDxAddress(representativeOrganisationDxAddress);

        return ccdApplicant;
    }

    public Map<String, Object> buildMap() {
        HashMap<String, Object> hashMap = new HashMap<>(propertiesMap);
        if (partyDetail != null) {
            hashMap.put("partyDetail", partyDetail.buildMap());
        }
        if (representativeOrganisationAddress != null) {
            hashMap.put("representativeOrganisationAddress", representativeOrganisationAddress.buildMap());
        }
        return hashMap;
    }

    public CcdApplicantBuilder partyDetail(CcdPartyBuilder partyDetail) {
        this.partyDetail = partyDetail;
        return this;
    }

    public CcdApplicantBuilder partyName(String partyName) {
        this.partyName = partyName;
        propertiesMap.put("partyName", partyName);
        return this;
    }

    public CcdApplicantBuilder representativeOrganisationName(String representativeOrganisationName) {
        this.representativeOrganisationName = representativeOrganisationName;
        propertiesMap.put("representativeOrganisationName", representativeOrganisationName);
        return this;
    }

    public CcdApplicantBuilder representativeOrganisationAddress(CcdAddressBuilder representativeOrganisationAddress) {
        this.representativeOrganisationAddress = representativeOrganisationAddress;
        return this;
    }

    public CcdApplicantBuilder representativeOrganisationPhone(String representativeOrganisationPhone) {
        this.representativeOrganisationPhone = representativeOrganisationPhone;
        propertiesMap.put("representativeOrganisationPhone", representativeOrganisationPhone);
        return this;
    }

    public CcdApplicantBuilder representativeOrganisationEmail(String representativeOrganisationEmail) {
        this.representativeOrganisationEmail = representativeOrganisationEmail;
        propertiesMap.put("representativeOrganisationEmail", representativeOrganisationEmail);
        return this;
    }

    public CcdApplicantBuilder representativeOrganisationDxAddress(String representativeOrganisationDxAddress) {
        this.representativeOrganisationDxAddress = representativeOrganisationDxAddress;
        propertiesMap.put("representativeOrganisationDxAddress", representativeOrganisationDxAddress);
        return this;
    }
}

