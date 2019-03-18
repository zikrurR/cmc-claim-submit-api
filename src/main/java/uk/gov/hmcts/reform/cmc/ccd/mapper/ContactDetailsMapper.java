package uk.gov.hmcts.reform.cmc.ccd.mapper;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;

import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ContactDetailsMapper
    implements BuilderMapper<CCDClaimant, ContactDetails, CCDClaimant.CCDClaimantBuilder> {

    @Override
    public void to(ContactDetails contactDetails, CCDClaimant.CCDClaimantBuilder builder) {

        contactDetails.getEmail().ifPresent(builder::representativeOrganisationEmail);
        contactDetails.getPhone().ifPresent(builder::representativeOrganisationPhone);
        contactDetails.getDxAddress().ifPresent(builder::representativeOrganisationDxAddress);
    }

    @Override
    public ContactDetails from(CCDClaimant ccdClaimant) {
        if (isBlank(ccdClaimant.getRepresentativeOrganisationPhone())
            && isBlank(ccdClaimant.getRepresentativeOrganisationEmail())
            && ccdClaimant.getRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        return new ContactDetails(
            ccdClaimant.getRepresentativeOrganisationPhone(),
            ccdClaimant.getRepresentativeOrganisationEmail(),
            ccdClaimant.getRepresentativeOrganisationDxAddress()
        );
    }
}
