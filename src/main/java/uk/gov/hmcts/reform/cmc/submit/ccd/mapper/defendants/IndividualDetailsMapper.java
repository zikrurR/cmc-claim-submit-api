package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

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

    public void to(IndividualDetails individual, CCDDefendant.CCDDefendantBuilder builder) {

        builder.claimantProvidedServiceAddress(addressMapper.to(individual.getServiceAddress()));
        builder.claimantProvidedDateOfBirth(individual.getDateOfBirth());
        builder.claimantProvidedEmail(individual.getEmail());
        builder.claimantProvidedName(individual.getName());
        builder.claimantProvidedAddress(addressMapper.to(individual.getAddress()));

        representativeMapper.to(individual.getRepresentative(), builder);
    }

    public IndividualDetails from(CCDCollectionElement<CCDDefendant> defendant) {
        CCDDefendant value = defendant.getValue();

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
