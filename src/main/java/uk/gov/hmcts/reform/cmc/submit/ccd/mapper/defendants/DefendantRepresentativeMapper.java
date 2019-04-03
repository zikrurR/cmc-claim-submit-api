package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class DefendantRepresentativeMapper
    implements BuilderMapper<CcdDefendant, Representative, CcdDefendant.CcdDefendantBuilder> {

    private final AddressMapper addressMapper;
    private final DefendantContactDetailsMapper defendantContactDetailsMapper;

    @Autowired
    public DefendantRepresentativeMapper(
        AddressMapper addressMapper,
        DefendantContactDetailsMapper defendantContactDetailsMapper
    ) {
        this.addressMapper = addressMapper;
        this.defendantContactDetailsMapper = defendantContactDetailsMapper;
    }

    @Override
    public void to(Representative representative, CcdDefendant.CcdDefendantBuilder builder) {
        if (representative == null) {
            return;
        }

        defendantContactDetailsMapper.to(representative.getOrganisationContactDetails(), builder);

        builder.claimantProvidedRepresentativeOrganisationName(representative.getOrganisationName());
        builder.claimantProvidedRepresentativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

    @Override
    public Representative from(CcdDefendant ccdDefendant) {
        if (isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationName())
            && ccdDefendant.getClaimantProvidedRepresentativeOrganisationAddress() == null
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail())
        ) {
            return null;
        }

        Representative representative = new Representative();
        representative.setOrganisationName(ccdDefendant.getClaimantProvidedRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressMapper.from(ccdDefendant.getClaimantProvidedRepresentativeOrganisationAddress()));
        representative.setOrganisationContactDetails(defendantContactDetailsMapper.from(ccdDefendant));

        return representative;

    }
}
