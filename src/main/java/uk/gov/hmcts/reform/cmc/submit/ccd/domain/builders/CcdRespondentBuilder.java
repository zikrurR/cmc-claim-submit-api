package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;

import java.util.HashMap;
import java.util.Map;

public class CcdRespondentBuilder implements Builder<CcdRespondent> {

    private CcdPartyBuilder claimantProvidedDetail;
    private String claimantProvidedPartyName;

    private String claimantProvidedRepresentativeOrganisationName;
    private CcdAddressBuilder claimantProvidedRepresentativeOrganisationAddress;
    private String claimantProvidedRepresentativeOrganisationPhone;
    private String claimantProvidedRepresentativeOrganisationEmail;
    private String claimantProvidedRepresentativeOrganisationDxAddress;


    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdRespondentBuilder builder() {
        return new CcdRespondentBuilder();
    }

    private CcdRespondentBuilder() {
    }

    @Override
    public CcdRespondent build() {
        return new CcdRespondent(claimantProvidedDetail.build(),
                                 claimantProvidedPartyName,
                                 claimantProvidedRepresentativeOrganisationName,
                                 claimantProvidedRepresentativeOrganisationAddress.build(),
                                 claimantProvidedRepresentativeOrganisationPhone,
                                 claimantProvidedRepresentativeOrganisationEmail,
                                 claimantProvidedRepresentativeOrganisationDxAddress);
    }

    public Map<String, Object> buildMap() {
        HashMap<String, Object> hashMap = new HashMap<>(propertiesMap);
        if (claimantProvidedDetail != null) {
            hashMap.put("claimantProvidedDetail", claimantProvidedDetail.buildMap());
        }
        if (claimantProvidedRepresentativeOrganisationAddress != null) {
            hashMap.put("claimantProvidedRepresentativeOrganisationAddress", claimantProvidedRepresentativeOrganisationAddress.buildMap());
        }
        return hashMap;
    }

    public CcdRespondentBuilder claimantProvidedDetail(CcdPartyBuilder claimantProvidedDetail) {
        this.claimantProvidedDetail = claimantProvidedDetail;
        return this;
    }

    public CcdRespondentBuilder claimantProvidedPartyName(String claimantProvidedPartyName) {
        this.claimantProvidedPartyName = claimantProvidedPartyName;
        propertiesMap.put("claimantProvidedPartyName", claimantProvidedPartyName);
        return this;
    }

    public CcdRespondentBuilder claimantProvidedRepresentativeOrganisationName(String claimantProvidedRepresentativeOrganisationName) {
        this.claimantProvidedRepresentativeOrganisationName = claimantProvidedRepresentativeOrganisationName;
        propertiesMap.put("claimantProvidedRepresentativeOrganisationName", claimantProvidedRepresentativeOrganisationName);
        return this;
    }

    public CcdRespondentBuilder claimantProvidedRepresentativeOrganisationAddress(CcdAddressBuilder claimantProvidedRepresentativeOrganisationAddress) {
        this.claimantProvidedRepresentativeOrganisationAddress = claimantProvidedRepresentativeOrganisationAddress;
        return this;
    }

    public CcdRespondentBuilder claimantProvidedRepresentativeOrganisationPhone(String claimantProvidedRepresentativeOrganisationPhone) {
        this.claimantProvidedRepresentativeOrganisationPhone = claimantProvidedRepresentativeOrganisationPhone;
        propertiesMap.put("claimantProvidedRepresentativeOrganisationPhone", claimantProvidedRepresentativeOrganisationPhone);
        return this;
    }

    public CcdRespondentBuilder claimantProvidedRepresentativeOrganisationEmail(String claimantProvidedRepresentativeOrganisationEmail) {
        this.claimantProvidedRepresentativeOrganisationEmail = claimantProvidedRepresentativeOrganisationEmail;
        propertiesMap.put("claimantProvidedRepresentativeOrganisationEmail", claimantProvidedRepresentativeOrganisationEmail);
        return this;
    }

    public CcdRespondentBuilder claimantProvidedRepresentativeOrganisationDxAddress(String claimantProvidedRepresentativeOrganisationDxAddress) {
        this.claimantProvidedRepresentativeOrganisationDxAddress = claimantProvidedRepresentativeOrganisationDxAddress;
        propertiesMap.put("claimantProvidedRepresentativeOrganisationDxAddress", claimantProvidedRepresentativeOrganisationDxAddress);
        return this;
    }
}