package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class DefendantContactDetailsMapper
    implements BuilderMapper<CcdDefendant, ContactDetails, CcdDefendant.CcdDefendantBuilder> {

    @Override
    public void to(ContactDetails contactDetails, CcdDefendant.CcdDefendantBuilder builder) {

        builder.claimantProvidedRepresentativeOrganisationEmail(contactDetails.getEmail());
        builder.claimantProvidedRepresentativeOrganisationPhone(contactDetails.getPhone());
        builder.claimantProvidedRepresentativeOrganisationDxAddress(contactDetails.getDxAddress());
    }

    @Override
    public ContactDetails from(CcdDefendant ccdDefendant) {
        if (isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail())
            && ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        ContactDetails build = new ContactDetails();

        build.setPhone(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone());
        build.setEmail(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail());
        build.setDxAddress(ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress());

        return build;
    }
}
