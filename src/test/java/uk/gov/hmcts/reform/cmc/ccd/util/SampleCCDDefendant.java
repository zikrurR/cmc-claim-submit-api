package uk.gov.hmcts.reform.cmc.ccd.util;

import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.COMPANY;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.INDIVIDUAL;
import static uk.gov.hmcts.reform.cmc.ccd.util.SampleData.getCcdAddress;

public class SampleCCDDefendant {

    private SampleCCDDefendant() {
        //Utility class
    }

    public static CCDDefendant.CCDDefendantBuilder withDefault() {
        return CCDDefendant.builder()
            .claimantProvidedType(INDIVIDUAL)
            .defendantId("defendantId")
            .letterHolderId("JCJEDU")
            .responseDeadline(now().plusDays(14))
            .partyEmail("defendant@Ididabadjob.com");
    }

    public static CCDDefendant.CCDDefendantBuilder withResponseMoreTimeNeededOption() {
        return withDefault();
    }

    private static CCDDefendant.CCDDefendantBuilder withParty() {
        return CCDDefendant.builder()
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

    public static CCDDefendant.CCDDefendantBuilder withPartyIndividual() {
        return withParty()
            .partyDateOfBirth(LocalDate.of(1980, 1, 1));
    }

    public static CCDDefendant.CCDDefendantBuilder withPartyCompany() {
        return withParty()
            .partyContactPerson("Mr Steven");
    }

    public static CCDDefendant.CCDDefendantBuilder withPartySoleTrader() {
        return withParty()
            .partyTitle("Mr")
            .partyBusinessName("Trading as name");
    }

    public static CCDDefendant.CCDDefendantBuilder withPartyOrganisation() {
        return withParty()
            .partyContactPerson("Mr Steven")
            .partyCompaniesHouseNumber("12345");
    }

}
