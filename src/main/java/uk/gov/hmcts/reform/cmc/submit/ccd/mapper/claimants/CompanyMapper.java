package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class CompanyMapper {

    private final AddressMapper addressMapper;
    private final RepresentativeMapper representativeMapper;

    @Autowired
    public CompanyMapper(AddressMapper addressMapper, RepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(Company company, CCDClaimant.CCDClaimantBuilder builder) {

        builder.partyPhone(company.getMobilePhone());
        builder.partyContactPerson(company.getContactPerson());
        builder.partyCorrespondenceAddress(addressMapper.to(company.getCorrespondenceAddress()));
        builder.partyName(company.getName());
        builder.partyAddress(addressMapper.to(company.getAddress()));


       representativeMapper.to(company.getRepresentative(), builder);

    }

    public Company from(CCDCollectionElement<CCDClaimant> claimant) {
        CCDClaimant value = claimant.getValue();
        Company company = new Company();

        company.setId(claimant.getId());
        company.setName(value.getPartyName());
        company.setAddress(addressMapper.from(value.getPartyAddress()));
        company.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        company.setMobilePhone(value.getPartyPhone());
        company.setRepresentative(representativeMapper.from(value));
        company.setContactPerson(value.getPartyContactPerson());

        return company;
    }
}
