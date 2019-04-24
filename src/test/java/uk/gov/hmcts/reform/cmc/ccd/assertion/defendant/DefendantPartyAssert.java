package uk.gov.hmcts.reform.cmc.ccd.assertion.defendant;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.cmc.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.cmc.ccd.domain.CcdPartyType.COMPANY;
import static uk.gov.hmcts.cmc.ccd.domain.CcdPartyType.INDIVIDUAL;
import static uk.gov.hmcts.cmc.ccd.domain.CcdPartyType.ORGANISATION;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class DefendantPartyAssert extends AbstractAssert<DefendantPartyAssert, Party> {

    public DefendantPartyAssert(Party party) {
        super(party, DefendantPartyAssert.class);
    }

    public DefendantPartyAssert isEqualTo(CcdRespondent ccdDefendant) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(INDIVIDUAL, ccdDefendant.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getClaimantProvidedDetail().getType(), INDIVIDUAL);
            }

            assertIndividual(ccdDefendant);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(ORGANISATION, ccdDefendant.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getClaimantProvidedDetail().getType(), ORGANISATION);
            }

            assertOrganisation(ccdDefendant);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(COMPANY, ccdDefendant.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getClaimantProvidedDetail().getType(), COMPANY);
            }

            assertCompany(ccdDefendant);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CcdPartyType.SOLE_TRADER, ccdDefendant.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getClaimantProvidedDetail().getType(), CcdPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdDefendant);

        }

        return this;
    }

    private void assertParty(CcdRespondent ccdDefendant) {
        if (!Objects.equals(actual.getName(), ccdDefendant.getClaimantProvidedPartyName())) {
            failWithMessage("Expected CcdDefendant.partyName to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedPartyName(), actual.getName());
        }
        assertThat(actual.getAddress()).isEqualTo(ccdDefendant.getClaimantProvidedDetail().getPrimaryAddress());
        assertThat(ccdDefendant.getClaimantProvidedDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdDefendant.getClaimantProvidedDetail().getTelephoneNumber().getTelephoneNumber())) {
            failWithMessage("Expected CcdDefendant.partyPhone to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getTelephoneNumber().getTelephoneNumber(), mobilePhone);
        }
    }

    private void assertSoleTrader(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);

        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(ccdDefendant.getClaimantProvidedDetail().getTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getBusinessName(), ccdDefendant.getClaimantProvidedDetail().getBusinessName())) {
            failWithMessage("Expected CcdDefendant.partyBusinessName to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getBusinessName(), actual.getBusinessName());
        }
    }

    private void assertCompany(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Company actual = (Company) this.actual;

        if (!Objects.equals(actual.getContactPerson(), ccdDefendant.getClaimantProvidedDetail().getContactPerson())) {
            failWithMessage("Expected CcdDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getContactPerson(), actual.getContactPerson());
        }
    }

    private void assertOrganisation(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Organisation actual = (Organisation) this.actual;

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdDefendant.getClaimantProvidedDetail().getContactPerson())) {
            failWithMessage("Expected CcdDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();
        if (!Objects.equals(companyHouseNumber, ccdDefendant.getClaimantProvidedDetail().getCompaniesHouseNumber())) {
            failWithMessage("Expected CcdDefendant.partyCompaniesHouseNumber to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getCompaniesHouseNumber(), companyHouseNumber);
        }
    }

    private void assertIndividual(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Individual actual = (Individual) this.actual;

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdDefendant.getClaimantProvidedDetail().getDateOfBirth())) {
            failWithMessage("Expected CcdDefendant.partyDateOfBirth to be <%s> but was <%s>",
                ccdDefendant.getClaimantProvidedDetail().getDateOfBirth(), actual.getDateOfBirth());
        }
    }
}
