package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

@Data
public class CcdRespondent {

    private CcdParty claimantProvidedDetail;
    private String claimantProvidedPartyName;

    private String claimantProvidedRepresentativeOrganisationName;
    private CcdAddress claimantProvidedRepresentativeOrganisationAddress;
    private String claimantProvidedRepresentativeOrganisationPhone;
    private String claimantProvidedRepresentativeOrganisationEmail;
    private String claimantProvidedRepresentativeOrganisationDxAddress;

}
