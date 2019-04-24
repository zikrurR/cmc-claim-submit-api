package uk.gov.hmcts.reform.cmc.ccd.assertion.defendant;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.cmc.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

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
            if (!Objects.equals(INDIVIDUAL, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), INDIVIDUAL);
            }

            assertIndividual(ccdDefendant);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(ORGANISATION, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), ORGANISATION);
            }

            assertOrganisation(ccdDefendant);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(COMPANY, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), COMPANY);
            }

            assertCompany(ccdDefendant);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CcdPartyType.SOLE_TRADER, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CcdDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), CcdPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdDefendant);

        }

        return this;
    }

    private void assertParty(CcdRespondent ccdDefendant) {
        if (!Objects.equals(actual.getName(), ccdDefendant.getPartyName())) {
            failWithMessage("Expected CcdDefendant.partyName to be <%s> but was <%s>",
                ccdDefendant.getPartyName(), actual.getName());
        }
        assertThat(actual.getAddress()).isEqualTo(ccdDefendant.getPartyAddress());
        assertThat(ccdDefendant.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdDefendant.getPartyPhone())) {
            failWithMessage("Expected CcdDefendant.partyPhone to be <%s> but was <%s>",
                ccdDefendant.getPartyPhone(), mobilePhone);
        }

        assertRepresentativeDetails(actual.getRepresentative(), ccdDefendant);
    }

    private void assertSoleTrader(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);

        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(ccdDefendant.getPartyTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getBusinessName(), ccdDefendant.getPartyBusinessName())) {
            failWithMessage("Expected CcdDefendant.partyBusinessName to be <%s> but was <%s>",
                ccdDefendant.getPartyBusinessName(), actual.getBusinessName());
        }
    }

    private void assertCompany(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Company actual = (Company) this.actual;

        if (!Objects.equals(actual.getContactPerson(), ccdDefendant.getPartyContactPerson())) {
            failWithMessage("Expected CcdDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getPartyContactPerson(), actual.getContactPerson());
        }
    }

    private void assertOrganisation(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Organisation actual = (Organisation) this.actual;

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdDefendant.getPartyContactPerson())) {
            failWithMessage("Expected CcdDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getPartyContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();
        if (!Objects.equals(companyHouseNumber, ccdDefendant.getPartyCompaniesHouseNumber())) {
            failWithMessage("Expected CcdDefendant.partyCompaniesHouseNumber to be <%s> but was <%s>",
                ccdDefendant.getPartyCompaniesHouseNumber(), companyHouseNumber);
        }
    }

    private void assertIndividual(CcdRespondent ccdDefendant) {
        assertParty(ccdDefendant);
        Individual actual = (Individual) this.actual;

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdDefendant.getPartyDateOfBirth())) {
            failWithMessage("Expected CcdDefendant.partyDateOfBirth to be <%s> but was <%s>",
                ccdDefendant.getPartyDateOfBirth(), actual.getDateOfBirth());
        }
    }

    private void assertRepresentativeDetails(Representative representative, CcdRespondent ccdParty) {
        if (!Objects.equals(representative.getOrganisationName(), ccdParty.getRepresentativeOrganisationName())) {
            failWithMessage("Expected CcdDefendant.representativeOrganisationName to be <%s> but was <%s>",
                ccdParty.getRepresentativeOrganisationName(), representative.getOrganisationName());
        }

        assertThat(representative.getOrganisationAddress()).isEqualTo(ccdParty.getRepresentativeOrganisationAddress());

        if (representative.getOrganisationContactDetails() != null) {
            ContactDetails contactDetails = representative.getOrganisationContactDetails();

            if (!Objects.equals(contactDetails.getDxAddress(), ccdParty.getRepresentativeOrganisationDxAddress())) {
                failWithMessage(
                    "Expected CcdDefendant.representativeOrganisationDxAddress to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationDxAddress(), contactDetails.getDxAddress());
            }

            if (!Objects.equals(contactDetails.getEmail(), ccdParty.getRepresentativeOrganisationEmail())) {
                failWithMessage(
                    "Expected CcdDefendant.representativeOrganisationEmail to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationEmail(), contactDetails.getEmail());
            }

            if (!Objects.equals(contactDetails.getPhone(), ccdParty.getRepresentativeOrganisationPhone())) {
                failWithMessage(
                    "Expected CcdDefendant.representativeOrganisationPhone to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationPhone(), contactDetails.getPhone());
            }
        }
    }
}
