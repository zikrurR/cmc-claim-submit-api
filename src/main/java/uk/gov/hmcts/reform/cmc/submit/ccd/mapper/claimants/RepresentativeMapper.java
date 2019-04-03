package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class RepresentativeMapper
    implements BuilderMapper<CcdClaimant, Representative, CcdClaimant.CcdClaimantBuilder> {

    private final AddressMapper addressMapper;
    private final ContactDetailsMapper contactDetailsMapper;

    @Autowired
    public RepresentativeMapper(AddressMapper addressMapper, ContactDetailsMapper contactDetailsMapper) {
        this.addressMapper = addressMapper;
        this.contactDetailsMapper = contactDetailsMapper;
    }

    @Override
    public void to(Representative representative, CcdClaimant.CcdClaimantBuilder builder) {
        if (representative == null) return;

        contactDetailsMapper.to(representative.getOrganisationContactDetails(), builder);

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

    @Override
    public Representative from(CcdClaimant ccdClaimant) {
        if (isBlank(ccdClaimant.getRepresentativeOrganisationName())
            && ccdClaimant.getRepresentativeOrganisationAddress() == null
            && isBlank(ccdClaimant.getRepresentativeOrganisationEmail())
            && isBlank(ccdClaimant.getRepresentativeOrganisationPhone())
            && isBlank(ccdClaimant.getRepresentativeOrganisationDxAddress())
        ) {
            return null;
        }

        Representative representative = new Representative();
        representative.setOrganisationName(ccdClaimant.getRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressMapper.from(ccdClaimant.getRepresentativeOrganisationAddress()));
        representative.setOrganisationContactDetails(contactDetailsMapper.from(ccdClaimant));

        return representative;

    }
}
