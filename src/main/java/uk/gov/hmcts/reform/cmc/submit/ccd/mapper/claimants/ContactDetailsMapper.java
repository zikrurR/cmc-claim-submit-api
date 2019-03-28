package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ContactDetailsMapper
    implements BuilderMapper<CcdClaimant, ContactDetails, CcdClaimant.CcdClaimantBuilder> {

    @Override
    public void to(ContactDetails contactDetails, CcdClaimant.CcdClaimantBuilder builder) {

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());
    }

    @Override
    public ContactDetails from(CcdClaimant ccdClaimant) {
        if (isBlank(ccdClaimant.getRepresentativeOrganisationPhone())
            && isBlank(ccdClaimant.getRepresentativeOrganisationEmail())
            && ccdClaimant.getRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail(ccdClaimant.getRepresentativeOrganisationEmail());
        contactDetails.setPhone(ccdClaimant.getRepresentativeOrganisationPhone());
        contactDetails.setDxAddress(ccdClaimant.getRepresentativeOrganisationDxAddress());

        return contactDetails;
    }
}
