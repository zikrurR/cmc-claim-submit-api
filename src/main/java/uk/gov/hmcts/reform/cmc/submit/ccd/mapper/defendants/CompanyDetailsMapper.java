package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class CompanyDetailsMapper {

    private final AddressMapper addressMapper;
    private final DefendantRepresentativeMapper representativeMapper;

    @Autowired
    public CompanyDetailsMapper(AddressMapper addressMapper, DefendantRepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(CompanyDetails company, CCDDefendant.CCDDefendantBuilder builder) {

        builder.claimantProvidedEmail(company.getEmail());
        builder.claimantProvidedContactPerson(company.getContactPerson());
        builder.claimantProvidedServiceAddress(addressMapper.to(company.getServiceAddress()));
        builder.claimantProvidedName(company.getName());
        builder.claimantProvidedAddress(addressMapper.to(company.getAddress()));

        representativeMapper.to(company.getRepresentative(), builder);

    }

    public CompanyDetails from(CCDCollectionElement<CCDDefendant> collectionElement) {
        CCDDefendant ccdDefendant = collectionElement.getValue();

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setId(collectionElement.getId());
        companyDetails.setName(ccdDefendant.getClaimantProvidedName());
        companyDetails.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedAddress()));
        companyDetails.setEmail(ccdDefendant.getClaimantProvidedEmail());
        companyDetails.setRepresentative(representativeMapper.from(ccdDefendant));
        companyDetails.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedServiceAddress()));
        companyDetails.setContactPerson(ccdDefendant.getClaimantProvidedContactPerson());

        return companyDetails;
    }
}
