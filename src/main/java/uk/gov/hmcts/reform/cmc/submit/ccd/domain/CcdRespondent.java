package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcdRespondent {

    private CcdParty claimantProvidedDetail;
    private String claimantProvidedPartyName;

    private String claimantProvidedRepresentativeOrganisationName;
    private CcdAddress claimantProvidedRepresentativeOrganisationAddress;
    private String claimantProvidedRepresentativeOrganisationPhone;
    private String claimantProvidedRepresentativeOrganisationEmail;
    private String claimantProvidedRepresentativeOrganisationDxAddress;

}
