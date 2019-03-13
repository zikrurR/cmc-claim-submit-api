package uk.gov.hmcts.reform.cmc.ccd.mapper;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleTheirDetails;
import uk.gov.hmcts.reform.cmc.ccd.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.CompanyDetailsMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.DefendantContactDetailsMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.DefendantRepresentativeMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.IndividualDetailsMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.OrganisationDetailsMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.SoleTraderDetailsMapper;
import uk.gov.hmcts.reform.cmc.ccd.mapper.TheirDetailsMapper;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDDefendantCompany;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDDefendantIndividual;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDDefendantOrganisation;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCCDDefendantSoleTrader;

public class TheirDetailsMapperTest {

    private TheirDetailsMapper theirDetailsMapper = new TheirDetailsMapper(
                                                            new IndividualDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new CompanyDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new OrganisationDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new SoleTraderDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())));

    @Test
    public void shouldMapIndividualToCCD() {
        //given
        TheirDetails party = SampleTheirDetails.builder().individualDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapCompanyToCCD() {
        //given
        TheirDetails party = SampleTheirDetails.builder().companyDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapOrganisationToCCD() {
        //given
        TheirDetails party = SampleTheirDetails.builder().organisationDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapSoleTraderToCCD() {
        //given
        TheirDetails party = SampleTheirDetails.builder().soleTraderDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapIndividualFromCCD() {
        //given
        CCDDefendant ccdParty = getCCDDefendantIndividual();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CCDCollectionElement.<CCDDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapCompanyFromCCD() {
        //given
        CCDDefendant ccdParty = getCCDDefendantCompany();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CCDCollectionElement.<CCDDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapOrganisationFromCCD() {
        //given
        CCDDefendant ccdParty = getCCDDefendantOrganisation();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CCDCollectionElement.<CCDDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapSoleTraderFromCCD() {
        //given
        CCDDefendant ccdParty = getCCDDefendantSoleTrader();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CCDCollectionElement.<CCDDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

}
