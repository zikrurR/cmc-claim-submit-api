package uk.gov.hmcts.reform.cmc.ccd.mapper.defendant;

import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.ccd.assertion.defendant.DefendantPartyAssert;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleParty;
import uk.gov.hmcts.reform.cmc.ccd.mapper.AddressMapper;

import org.junit.Test;

import static uk.gov.hmcts.reform.cmc.ccd.util.SampleCCDDefendant.withPartyCompany;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleCCDDefendant.withPartyIndividual;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleCCDDefendant.withPartyOrganisation;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleCCDDefendant.withPartySoleTrader;

public class DefendantPartyMapperTest {

    private DefendantPartyMapper mapper = new DefendantPartyMapper(new AddressMapper());

    private DefendantPartyAssert assertThat(Party party) {
        return new DefendantPartyAssert(party);
    }

    @Test(expected = NullPointerException.class)
    public void mapToShouldThrowExceptionWhenBuilderIsNull() {
        mapper.to(null, SampleParty.builder().individual());
    }

    @Test(expected = NullPointerException.class)
    public void mapToShouldThrowExceptionWhenPartyIsNull() {
        mapper.to(CCDDefendant.builder(), null);
    }

    @Test
    public void shouldMapIndividualToCCD() {
        //given
        Party party = SampleParty.builder().individual();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        mapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapCompanyToCCD() {
        //given
        Party party = SampleParty.builder().company();

        //when
        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        mapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapOrganisationToCCD() {
        //given
        Party party = SampleParty.builder().organisation();

        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        mapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapSoleTraderToCCD() {
        //given
        Party party = SampleParty.builder().soleTrader();

        CCDDefendant.CCDDefendantBuilder builder = CCDDefendant.builder();
        mapper.to(builder, party);

        //then
        assertThat(party).isEqualTo(builder.build());
    }

    @Test
    public void shouldMapIndividualFromCCD() {
        //given
        CCDDefendant ccdDefendant = withPartyIndividual().build();

        //when
        Party party = mapper.from(ccdDefendant);

        //then
        assertThat(party).isEqualTo(ccdDefendant);
    }

    @Test
    public void shouldMapCompanyFromCCD() {
        //given
        CCDDefendant ccdDefendant = withPartyCompany().build();

        //when
        Party party = mapper.from(ccdDefendant);

        //then
        assertThat(party).isEqualTo(ccdDefendant);
    }

    @Test
    public void shouldMapSoleTraderFromCCD() {
        //given
        CCDDefendant ccdDefendant = withPartySoleTrader().build();

        //when
        Party party = mapper.from(ccdDefendant);

        //then
        assertThat(party).isEqualTo(ccdDefendant);
    }

    @Test
    public void shouldMapOrganisationFromCCD() {
        //given
        CCDDefendant ccdDefendant = withPartyOrganisation().build();

        //when
        Party party = mapper.from(ccdDefendant);

        //then
        assertThat(party).isEqualTo(ccdDefendant);
    }
}
