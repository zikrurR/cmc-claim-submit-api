package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class OrganisationMapper {

    private final AddressMapper addressMapper;
    private final RepresentativeMapper representativeMapper;

    @Autowired
    public OrganisationMapper(AddressMapper addressMapper, RepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(Organisation organisation, CCDClaimant.CCDClaimantBuilder builder) {
        if (organisation == null) return;

        builder.partyCorrespondenceAddress(addressMapper.to(organisation.getCorrespondenceAddress()));

        representativeMapper.to(organisation.getRepresentative(), builder);

        builder.partyPhone(organisation.getMobilePhone());
        builder.partyContactPerson(organisation.getContactPerson());
        builder.partyCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.partyName(organisation.getName());
        builder.partyAddress(addressMapper.to(organisation.getAddress()));

    }

    public Organisation from(CCDCollectionElement<CCDClaimant> claimant) {
        if (claimant == null) return null;

        CCDClaimant value = claimant.getValue();

        Organisation organisation = new Organisation();

        organisation.setId(claimant.getId());
        organisation.setName(value.getPartyName());
        organisation.setAddress(addressMapper.from(value.getPartyAddress()));
        organisation.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        organisation.setMobilePhone(value.getPartyPhone());
        organisation.setRepresentative(representativeMapper.from(value));
        organisation.setContactPerson(value.getPartyContactPerson());
        organisation.setCompaniesHouseNumber(value.getPartyCompaniesHouseNumber());

        return organisation;
    }
}
