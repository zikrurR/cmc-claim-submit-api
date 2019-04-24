package uk.gov.hmcts.reform.cmc.ccd.util;

import uk.gov.hmcts.cmc.ccd.domain.CcdRespondent;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static uk.gov.hmcts.cmc.ccd.domain.CcdPartyType.COMPANY;
import static uk.gov.hmcts.cmc.ccd.domain.CcdPartyType.INDIVIDUAL;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdAddress;

public class SampleCcdDefendant {

    private SampleCcdDefendant() {
        //Utility class
    }

    public static CcdRespondent.CcdRespondentBuilder withDefault() {
        return CcdRespondent.builder()
            .claimantProvidedType(INDIVIDUAL)
            .defendantId("defendantId")
            .letterHolderId("JCJEDU")
            .responseDeadline(now().plusDays(14))
            .partyEmail("defendant@Ididabadjob.com");
    }

    public static CcdRespondent.CcdRespondentBuilder withResponseMoreTimeNeededOption() {
        return withDefault();
    }

    private static CcdRespondent.CcdRespondentBuilder withParty() {
        return CcdRespondent.builder()
            .partyType(COMPANY)
            .partyName("Mr Norman")
            .partyAddress(getCcdAddress())
            .partyCorrespondenceAddress(getCcdAddress())
            .partyPhone("07123456789")
            .representativeOrganisationName("Trading ltd")
            .representativeOrganisationAddress(getCcdAddress())
            .representativeOrganisationPhone("07123456789")
            .representativeOrganisationEmail("representative@example.org")
            .representativeOrganisationDxAddress("DX123456");
    }

    public static CcdRespondent.CcdRespondentBuilder withPartyIndividual() {
        return withParty()
            .partyDateOfBirth(LocalDate.of(1980, 1, 1));
    }

    public static CcdRespondent.CcdRespondentBuilder withPartyCompany() {
        return withParty()
            .partyContactPerson("Mr Steven");
    }

    public static CcdRespondent.CcdRespondentBuilder withPartySoleTrader() {
        return withParty()
            .partyTitle("Mr")
            .partyBusinessName("Trading as name");
    }

    public static CcdRespondent.CcdRespondentBuilder withPartyOrganisation() {
        return withParty()
            .partyContactPerson("Mr Steven")
            .partyCompaniesHouseNumber("12345");
    }

}
