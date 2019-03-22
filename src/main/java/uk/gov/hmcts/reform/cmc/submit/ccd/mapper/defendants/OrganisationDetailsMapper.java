package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class OrganisationDetailsMapper {

    private final AddressMapper addressMapper;
    private final DefendantRepresentativeMapper representativeMapper;

    @Autowired
    public OrganisationDetailsMapper(AddressMapper addressMapper, DefendantRepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(OrganisationDetails organisation, CCDDefendant.CCDDefendantBuilder builder) {
        if (organisation == null) return;

        builder.claimantProvidedServiceAddress(addressMapper.to(organisation.getServiceAddress()));
        representativeMapper.to(organisation.getRepresentative(), builder);

        builder.claimantProvidedContactPerson(organisation.getContactPerson());
        builder.claimantProvidedCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.claimantProvidedEmail(organisation.getEmail());
        builder.claimantProvidedName(organisation.getName());
        builder.claimantProvidedAddress(addressMapper.to(organisation.getAddress()));

    }

    public OrganisationDetails from(CCDCollectionElement<CCDDefendant> defendant) {
        if (defendant == null) return null;

        CCDDefendant value = defendant.getValue();

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setId(defendant.getId());
        organisationDetails.setName(value.getClaimantProvidedName());
        organisationDetails.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        organisationDetails.setEmail(value.getClaimantProvidedEmail());
        organisationDetails.setRepresentative(representativeMapper.from(value));
        organisationDetails.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        organisationDetails.setContactPerson(value.getClaimantProvidedContactPerson());
        organisationDetails.setCompaniesHouseNumber(value.getClaimantProvidedCompaniesHouseNumber());

        return organisationDetails;
    }
}
