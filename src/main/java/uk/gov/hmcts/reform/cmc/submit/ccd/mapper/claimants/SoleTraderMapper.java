package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;

@Component
public class SoleTraderMapper {

    private final AddressMapper addressMapper;
    private final RepresentativeMapper representativeMapper;

    @Autowired
    public SoleTraderMapper(AddressMapper addressMapper, RepresentativeMapper representativeMapper) {
        this.addressMapper = addressMapper;
        this.representativeMapper = representativeMapper;
    }

    public void to(SoleTrader soleTrader, CcdClaimant.CcdClaimantBuilder builder) {

        builder.partyTitle(soleTrader.getTitle());
        builder.partyPhone(soleTrader.getMobilePhone());
        builder.partyBusinessName(soleTrader.getBusinessName());
        builder.partyCorrespondenceAddress(addressMapper.to(soleTrader.getCorrespondenceAddress()));
        builder.partyName(soleTrader.getName());
        builder.partyAddress(addressMapper.to(soleTrader.getAddress()));

        representativeMapper.to(soleTrader.getRepresentative(), builder);

    }

    public SoleTrader from(CcdCollectionElement<CcdClaimant> claimant) {
        CcdClaimant value = claimant.getValue();
        SoleTrader soletrader = new SoleTrader();

        soletrader.setId(claimant.getId());
        soletrader.setName(value.getPartyName());
        soletrader.setAddress(addressMapper.from(value.getPartyAddress()));
        soletrader.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        soletrader.setMobilePhone(value.getPartyPhone());
        soletrader.setRepresentative(representativeMapper.from(value));
        soletrader.setTitle(value.getPartyTitle());
        soletrader.setBusinessName(value.getPartyBusinessName());

        return soletrader;
    }
}
