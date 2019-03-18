package uk.gov.hmcts.reform.cmc.ccd.mapper;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleParty;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDClaimantCompany;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDClaimantIndividual;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDClaimantOrganisation;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDClaimantSoleTrader;

public class ClaimantMapperTest {

    @Autowired
    private ClaimantMapper claimantMapper = new ClaimantMapper(
                                            new IndividualMapper(new AddressMapper(), new RepresentativeMapper(new AddressMapper(), new ContactDetailsMapper())),
                                            new CompanyMapper(new AddressMapper(), new RepresentativeMapper(new AddressMapper(), new ContactDetailsMapper())),
                                            new OrganisationMapper(new AddressMapper(), new RepresentativeMapper(new AddressMapper(), new ContactDetailsMapper())),
                                            new SoleTraderMapper(new AddressMapper(), new RepresentativeMapper(new AddressMapper(), new ContactDetailsMapper())));

    @Test
    public void shouldMapIndividualToCCD() {
        //given
        Party party = SampleParty.builder().individual();

        //when
        CCDCollectionElement<CCDClaimant> ccdParty = claimantMapper.to(party);
        CCDClaimant ccdClaimant = ccdParty.getValue();

        //then
        assertThat(party).isEqualTo(ccdClaimant);
    }

    @Test
    public void shouldMapCompanyToCCD() {
        //given
        Party party = SampleParty.builder().company();

        //when
        CCDCollectionElement<CCDClaimant> ccdParty = claimantMapper.to(party);
        CCDClaimant ccdClaimant = ccdParty.getValue();

        //then
        assertThat(party).isEqualTo(ccdClaimant);
    }

    @Test
    public void shouldMapOrganisationToCCD() {
        //given
        Party party = SampleParty.builder().organisation();

        //when
        CCDCollectionElement<CCDClaimant> ccdParty = claimantMapper.to(party);
        CCDClaimant ccdClaimant = ccdParty.getValue();

        //then
        assertThat(party).isEqualTo(ccdClaimant);
    }

    @Test
    public void shouldMapSoleTraderToCCD() {
        //given
        Party party = SampleParty.builder().soleTrader();

        //when
        CCDCollectionElement<CCDClaimant> ccdParty = claimantMapper.to(party);
        CCDClaimant ccdClaimant = ccdParty.getValue();

        //then
        assertThat(party).isEqualTo(ccdClaimant);
    }

    @Test
    public void shouldMapIndividualFromCCD() {
        //given
        CCDClaimant ccdParty = getCCDClaimantIndividual();
        String collectionId = UUID.randomUUID().toString();

        //when
        Party party = claimantMapper
            .from(CCDCollectionElement.<CCDClaimant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapCompanyFromCCD() {
        //given
        CCDClaimant ccdParty = getCCDClaimantCompany();
        String collectionId = UUID.randomUUID().toString();

        //when
        Party party = claimantMapper
            .from(CCDCollectionElement.<CCDClaimant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapOrganisationFromCCD() {
        //given
        CCDClaimant ccdParty = getCCDClaimantOrganisation();
        String collectionId = UUID.randomUUID().toString();

        //when
        Party party = claimantMapper
            .from(CCDCollectionElement.<CCDClaimant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapSoleTraderFromCCD() {
        //given
        CCDClaimant ccdParty = getCCDClaimantSoleTrader();
        String collectionId = UUID.randomUUID().toString();

        //when
        Party party = claimantMapper
            .from(CCDCollectionElement.<CCDClaimant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

}
