package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleTheirDetails;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.CompanyDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.DefendantContactDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.DefendantRepresentativeMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.IndividualDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.OrganisationDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.SoleTraderDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.TheirDetailsMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
        TheirDetails party = SampleTheirDetails.individualDetails();

        //when
        CcdDefendant.CcdDefendantBuilder builder = CcdDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapCompanyToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.companyDetails();

        //when
        CcdDefendant.CcdDefendantBuilder builder = CcdDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapOrganisationToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.organisationDetails();

        //when
        CcdDefendant.CcdDefendantBuilder builder = CcdDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapSoleTraderToCcd() {
        //given
        TheirDetails party = SampleTheirDetails.soleTraderDetails();

        //when
        CcdDefendant.CcdDefendantBuilder builder = CcdDefendant.builder();
        theirDetailsMapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapIndividualFromCcd() {
        //given
        CcdDefendant ccdParty = getCcdDefendantIndividual();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CcdCollectionElement.<CcdDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapCompanyFromCcd() {
        //given
        CcdDefendant ccdParty = getCcdDefendantCompany();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CcdCollectionElement.<CcdDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapOrganisationFromCcd() {
        //given
        CcdDefendant ccdParty = getCcdDefendantOrganisation();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CcdCollectionElement.<CcdDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

    @Test
    public void shouldMapSoleTraderFromCcd() {
        //given
        CcdDefendant ccdParty = getCcdDefendantSoleTrader();
        String collectionId = UUID.randomUUID().toString();

        //when
        TheirDetails party = theirDetailsMapper
            .from(CcdCollectionElement.<CcdDefendant>builder()
                .id(collectionId)
                .value(ccdParty).build());

        //then
        assertThat(party).isEqualTo(ccdParty);
        assertThat(party.getId()).isEqualTo(collectionId);
    }

}
