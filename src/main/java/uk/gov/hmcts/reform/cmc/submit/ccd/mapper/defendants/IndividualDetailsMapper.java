package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;

@Component
public class IndividualDetailsMapper {

    private final AddressMapper addressMapper;
    private final DefendantRepresentativeMapper representativeMapper;

    @Autowired
    public IndividualDetailsMapper(AddressMapper addressMapper,
                                   DefendantRepresentativeMapper defendantRepresentativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = defendantRepresentativeMapper;
    }

    public void to(IndividualDetails individual, CcdDefendant.CcdDefendantBuilder builder) {

        builder.claimantProvidedServiceAddress(addressMapper.to(individual.getServiceAddress()));
        builder.claimantProvidedDateOfBirth(individual.getDateOfBirth());
        builder.claimantProvidedEmail(individual.getEmail());
        builder.claimantProvidedName(individual.getName());
        builder.claimantProvidedAddress(addressMapper.to(individual.getAddress()));

        representativeMapper.to(individual.getRepresentative(), builder);
    }

    public IndividualDetails from(CcdCollectionElement<CcdDefendant> defendant) {
        CcdDefendant value = defendant.getValue();

        IndividualDetails individualDetails = new IndividualDetails();
        individualDetails.setId(defendant.getId());
        individualDetails.setName(value.getClaimantProvidedName());
        individualDetails.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        individualDetails.setEmail(value.getClaimantProvidedEmail());
        individualDetails.setRepresentative(representativeMapper.from(value));
        individualDetails.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        individualDetails.setDateOfBirth(value.getClaimantProvidedDateOfBirth());

        return individualDetails;
    }
}
