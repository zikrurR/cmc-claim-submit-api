package uk.gov.hmcts.reform.cmc.ccd.assertion.defendant;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CCDPartyType;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.claimants.Company;
import uk.gov.hmcts.cmc.domain.models.claimants.Individual;
import uk.gov.hmcts.cmc.domain.models.claimants.Organisation;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.cmc.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;
import uk.gov.hmcts.cmc.domain.models.common.Representative;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.COMPANY;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.INDIVIDUAL;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.ORGANISATION;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class DefendantPartyAssert extends AbstractAssert<DefendantPartyAssert, Party> {

    public DefendantPartyAssert(Party party) {
        super(party, DefendantPartyAssert.class);
    }

    public DefendantPartyAssert isEqualTo(CCDDefendant ccdDefendant) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(INDIVIDUAL, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CCDDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), INDIVIDUAL);
            }

            assertIndividual(ccdDefendant);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(ORGANISATION, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CCDDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), ORGANISATION);
            }

            assertOrganisation(ccdDefendant);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(COMPANY, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CCDDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), COMPANY);
            }

            assertCompany(ccdDefendant);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CCDPartyType.SOLE_TRADER, ccdDefendant.getPartyType())) {
                failWithMessage("Expected CCDDefendant.partyType to be <%s> but was <%s>",
                    ccdDefendant.getPartyType(), CCDPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdDefendant);

        }

        return this;
    }

    private void assertParty(CCDDefendant ccdDefendant) {
        if (!Objects.equals(actual.getName(), ccdDefendant.getPartyName())) {
            failWithMessage("Expected CCDDefendant.partyName to be <%s> but was <%s>",
                ccdDefendant.getPartyName(), actual.getName());
        }
        assertThat(actual.getAddress()).isEqualTo(ccdDefendant.getPartyAddress());
        assertThat(ccdDefendant.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdDefendant.getPartyPhone())) {
            failWithMessage("Expected CCDDefendant.partyPhone to be <%s> but was <%s>",
                ccdDefendant.getPartyPhone(), mobilePhone);
        }

        assertRepresentativeDetails(actual.getRepresentative(), ccdDefendant);
    }

    private void assertSoleTrader(CCDDefendant ccdDefendant) {
        assertParty(ccdDefendant);

        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(ccdDefendant.getPartyTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getBusinessName(), ccdDefendant.getPartyBusinessName())) {
            failWithMessage("Expected CCDDefendant.partyBusinessName to be <%s> but was <%s>",
                ccdDefendant.getPartyBusinessName(), actual.getBusinessName());
        }
    }

    private void assertCompany(CCDDefendant ccdDefendant) {
        assertParty(ccdDefendant);
        Company actual = (Company) this.actual;

        if (!Objects.equals(actual.getContactPerson(), ccdDefendant.getPartyContactPerson())) {
            failWithMessage("Expected CCDDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getPartyContactPerson(), actual.getContactPerson());
        }
    }

    private void assertOrganisation(CCDDefendant ccdDefendant) {
        assertParty(ccdDefendant);
        Organisation actual = (Organisation) this.actual;

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdDefendant.getPartyContactPerson())) {
            failWithMessage("Expected CCDDefendant.partyContactPerson to be <%s> but was <%s>",
                ccdDefendant.getPartyContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();
        if (!Objects.equals(companyHouseNumber, ccdDefendant.getPartyCompaniesHouseNumber())) {
            failWithMessage("Expected CCDDefendant.partyCompaniesHouseNumber to be <%s> but was <%s>",
                ccdDefendant.getPartyCompaniesHouseNumber(), companyHouseNumber);
        }
    }

    private void assertIndividual(CCDDefendant ccdDefendant) {
        assertParty(ccdDefendant);
        Individual actual = (Individual) this.actual;

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdDefendant.getPartyDateOfBirth())) {
            failWithMessage("Expected CCDDefendant.partyDateOfBirth to be <%s> but was <%s>",
                ccdDefendant.getPartyDateOfBirth(), actual.getDateOfBirth());
        }
    }

    private void assertRepresentativeDetails(Representative representative, CCDDefendant ccdParty) {
        if (!Objects.equals(representative.getOrganisationName(), ccdParty.getRepresentativeOrganisationName())) {
            failWithMessage("Expected CCDDefendant.representativeOrganisationName to be <%s> but was <%s>",
                ccdParty.getRepresentativeOrganisationName(), representative.getOrganisationName());
        }

        assertThat(representative.getOrganisationAddress()).isEqualTo(ccdParty.getRepresentativeOrganisationAddress());

        if (representative.getOrganisationContactDetails() != null) {
            ContactDetails contactDetails = representative.getOrganisationContactDetails();

            if (!Objects.equals(contactDetails.getDxAddress(), ccdParty.getRepresentativeOrganisationDxAddress())) {
                failWithMessage(
                    "Expected CCDDefendant.representativeOrganisationDxAddress to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationDxAddress(), contactDetails.getDxAddress());
            }

            if (!Objects.equals(contactDetails.getEmail(), ccdParty.getRepresentativeOrganisationEmail())) {
                failWithMessage(
                    "Expected CCDDefendant.representativeOrganisationEmail to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationEmail(), contactDetails.getEmail());
            }

            if (!Objects.equals(contactDetails.getPhone(), ccdParty.getRepresentativeOrganisationPhone())) {
                failWithMessage(
                    "Expected CCDDefendant.representativeOrganisationPhone to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationPhone(), contactDetails.getPhone());
            }
        }
    }
}
