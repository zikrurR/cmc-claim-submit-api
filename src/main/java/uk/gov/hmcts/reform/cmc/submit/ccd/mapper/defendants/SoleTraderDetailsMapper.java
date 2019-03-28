package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class SoleTraderDetailsMapper {

    private final AddressMapper addressMapper;
    private final DefendantRepresentativeMapper representativeMapper;

    @Autowired
    public SoleTraderDetailsMapper(AddressMapper addressMapper, DefendantRepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(SoleTraderDetails soleTrader, CcdDefendant.CcdDefendantBuilder builder) {
        if (soleTrader == null) {
            return;
        }

        builder.claimantProvidedTitle(soleTrader.getTitle());
        builder.claimantProvidedBusinessName(soleTrader.getBusinessName());

        representativeMapper.to(soleTrader.getRepresentative(), builder);
        builder.claimantProvidedEmail(soleTrader.getEmail());
        builder.claimantProvidedServiceAddress(addressMapper.to(soleTrader.getServiceAddress()));

        builder.claimantProvidedName(soleTrader.getName());
        builder.claimantProvidedAddress(addressMapper.to(soleTrader.getAddress()));

    }

    public SoleTraderDetails from(CcdCollectionElement<CcdDefendant> defendant) {
        if (defendant == null) {
            return null;
        }

        CcdDefendant value = defendant.getValue();

        SoleTraderDetails ccdSoleTrader = new SoleTraderDetails();
        ccdSoleTrader.setId(defendant.getId());
        ccdSoleTrader.setName(value.getClaimantProvidedName());
        ccdSoleTrader.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        ccdSoleTrader.setEmail(value.getClaimantProvidedEmail());
        ccdSoleTrader.setRepresentative(representativeMapper.from(value));
        ccdSoleTrader.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        ccdSoleTrader.setTitle(value.getClaimantProvidedTitle());
        ccdSoleTrader.setBusinessName(value.getClaimantProvidedBusinessName());

        return ccdSoleTrader;
    }
}
