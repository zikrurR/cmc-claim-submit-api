package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;

@Component
public class OrganisationMapper {

    private final AddressMapper addressMapper;
    private final RepresentativeMapper representativeMapper;

    @Autowired
    public OrganisationMapper(AddressMapper addressMapper, RepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(Organisation organisation, CcdClaimant.CcdClaimantBuilder builder) {
        if (organisation == null) return;

        builder.partyCorrespondenceAddress(addressMapper.to(organisation.getCorrespondenceAddress()));

        representativeMapper.to(organisation.getRepresentative(), builder);

        builder.partyPhone(organisation.getMobilePhone());
        builder.partyContactPerson(organisation.getContactPerson());
        builder.partyCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.partyName(organisation.getName());
        builder.partyAddress(addressMapper.to(organisation.getAddress()));

    }

    public Organisation from(CcdCollectionElement<CcdClaimant> claimant) {
        if (claimant == null) return null;

        CcdClaimant value = claimant.getValue();

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
