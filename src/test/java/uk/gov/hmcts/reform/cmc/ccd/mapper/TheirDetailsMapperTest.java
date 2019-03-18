package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.Test;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleTheirDetails;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdDefendantCompany;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdDefendantIndividual;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdDefendantOrganisation;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdDefendantSoleTrader;

public class TheirDetailsMapperTest {

    private TheirDetailsMapper theirDetailsMapper = new TheirDetailsMapper(
                                                            new IndividualDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new CompanyDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new OrganisationDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())),
                                                            new SoleTraderDetailsMapper(new AddressMapper(), new DefendantRepresentativeMapper(new AddressMapper(), new DefendantContactDetailsMapper())));

    @Test
    public void shouldMapIndividualToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.builder().individualDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapCompanyToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.builder().companyDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapOrganisationToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.builder().organisationDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapSoleTraderToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.builder().soleTraderDetails();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapIndividualFromCcd() {
        //given
        CCDDefendant ccdParty = getCcdDefendantIndividual();
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
    public void shouldMapCompanyFromCcd() {
        //given
        CCDDefendant ccdParty = getCcdDefendantCompany();
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
    public void shouldMapOrganisationFromCcd() {
        //given
        CCDDefendant ccdParty = getCcdDefendantOrganisation();
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
    public void shouldMapSoleTraderFromCcd() {
        //given
        CCDDefendant ccdParty = getCcdDefendantSoleTrader();
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
