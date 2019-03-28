package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.cmc.domain.models.claimants.Company;
import uk.gov.hmcts.cmc.domain.models.claimants.Individual;
import uk.gov.hmcts.cmc.domain.models.claimants.Organisation;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.cmc.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;
import uk.gov.hmcts.cmc.domain.models.common.Representative;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class ClaimantAssert extends AbstractAssert<ClaimantAssert, Party> {

    public ClaimantAssert(Party party) {
        super(party, ClaimantAssert.class);
    }

    public ClaimantAssert isEqualTo(CcdClaimant ccdParty) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(CcdPartyType.INDIVIDUAL, ccdParty.getPartyType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CcdPartyType.INDIVIDUAL);
            }

            assertIndividual(ccdParty);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(CcdPartyType.ORGANISATION, ccdParty.getPartyType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CcdPartyType.ORGANISATION);
            }

            assertOrganisation(ccdParty);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(CcdPartyType.COMPANY, ccdParty.getPartyType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CcdPartyType.COMPANY);
            }

            assertCompany(ccdParty);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CcdPartyType.SOLE_TRADER, ccdParty.getPartyType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CcdPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdParty);

        }

        return this;
    }

    private void assertSoleTrader(CcdClaimant ccdParty) {
        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyAddress());

        assertThat(ccdParty.getPartyTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdSoleTrader.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), this.actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CcdCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        if (!Objects.equals(actual.getBusinessName(), ccdParty.getPartyBusinessName())) {
            failWithMessage("Expected CcdSoleTrader.businessName to be <%s> but was <%s>",
                ccdParty.getPartyBusinessName(), actual.getBusinessName());
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertCompany(CcdClaimant ccdParty) {
        Company actual = (Company) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdCompany.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CcdCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        if (!Objects.equals(actual.getContactPerson(), ccdParty.getPartyContactPerson())) {
            failWithMessage("Expected CcdCompany.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyContactPerson(), actual.getContactPerson());
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertOrganisation(CcdClaimant ccdParty) {
        Organisation actual = (Organisation) this.actual;

        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdOrganisation.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CcdOrganisation.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdParty.getPartyContactPerson())) {
            failWithMessage("Expected CcdOrganisation.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();

        if (!Objects.equals(companyHouseNumber, ccdParty.getPartyCompaniesHouseNumber())) {
            failWithMessage("Expected CcdOrganisation.companyHouseNumber to be <%s> but was <%s>",
                ccdParty.getPartyCompaniesHouseNumber(), companyHouseNumber);
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertIndividual(CcdClaimant ccdParty) {
        Individual actual = (Individual) this.actual;

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdIndividual.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdParty.getPartyDateOfBirth())) {
            failWithMessage("Expected CcdIndividual.dateOfBirth to be <%s> but was <%s>",
                ccdParty.getPartyDateOfBirth(), actual.getDateOfBirth());

        }
        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyAddress());

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertRepresentativeDetails(Representative representative, CcdClaimant ccdParty) {
        if (!Objects.equals(representative.getOrganisationName(), ccdParty.getRepresentativeOrganisationName())) {
            failWithMessage("Expected Representative.organisationName to be <%s> but was <%s>",
                ccdParty.getRepresentativeOrganisationName(), representative.getOrganisationName());
        }

        assertThat(representative.getOrganisationAddress())
            .isEqualTo(ccdParty.getRepresentativeOrganisationAddress());

        if (representative.getOrganisationContactDetails() != null) {
            ContactDetails contactDetails = representative.getOrganisationContactDetails();

            if (!Objects.equals(contactDetails.getDxAddress(), ccdParty.getRepresentativeOrganisationDxAddress())) {
                failWithMessage("Expected Representative.organisationDxAddress to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationDxAddress(), contactDetails.getDxAddress());
            }

            if (!Objects.equals(contactDetails.getEmail(), ccdParty.getRepresentativeOrganisationEmail())) {
                failWithMessage("Expected Representative.organisationEmail to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationEmail(), contactDetails.getEmail());
            }

            if (!Objects.equals(contactDetails.getPhone(), ccdParty.getRepresentativeOrganisationPhone())) {
                failWithMessage("Expected Representative.organisationPhone to be <%s> but was <%s>",
                    ccdParty.getRepresentativeOrganisationPhone(), contactDetails.getPhone());
            }
        }

    }
}
