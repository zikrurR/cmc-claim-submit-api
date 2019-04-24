package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class ClaimantAssert extends AbstractAssert<ClaimantAssert, Party> {

    public ClaimantAssert(Party party) {
        super(party, ClaimantAssert.class);
    }

    public ClaimantAssert isEqualTo(CcdApplicant ccdParty) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(CcdPartyType.INDIVIDUAL, ccdParty.getPartyDetail().getType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyDetail().getType(), CcdPartyType.INDIVIDUAL);
            }

            assertIndividual(ccdParty);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(CcdPartyType.ORGANISATION, ccdParty.getPartyDetail().getType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyDetail().getType(), CcdPartyType.ORGANISATION);
            }

            assertOrganisation(ccdParty);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(CcdPartyType.COMPANY, ccdParty.getPartyDetail().getType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyDetail().getType(), CcdPartyType.COMPANY);
            }

            assertCompany(ccdParty);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CcdPartyType.SOLE_TRADER, ccdParty.getPartyDetail().getType())) {
                failWithMessage("Expected CcdClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyDetail().getType(), CcdPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdParty);

        }

        return this;
    }

    private void assertSoleTrader(CcdApplicant ccdParty) {
        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyDetail().getPrimaryAddress());

        assertThat(ccdParty.getPartyDetail().getTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdSoleTrader.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), this.actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber())) {
            failWithMessage("Expected CcdCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber(), mobilePhone);
        }

        if (!Objects.equals(actual.getBusinessName(), ccdParty.getPartyDetail().getBusinessName())) {
            failWithMessage("Expected CcdSoleTrader.businessName to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getBusinessName(), actual.getBusinessName());
        }

        assertThat(ccdParty.getPartyDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertCompany(CcdApplicant ccdParty) {
        Company actual = (Company) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyDetail().getPrimaryAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdCompany.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber())) {
            failWithMessage("Expected CcdCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber(), mobilePhone);
        }

        if (!Objects.equals(actual.getContactPerson(), ccdParty.getPartyDetail().getContactPerson())) {
            failWithMessage("Expected CcdCompany.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getContactPerson(), actual.getContactPerson());
        }

        assertThat(ccdParty.getPartyDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertOrganisation(CcdApplicant ccdParty) {
        Organisation actual = (Organisation) this.actual;

        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyDetail().getPrimaryAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdOrganisation.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber())) {
            failWithMessage("Expected CcdOrganisation.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getTelephoneNumber().getTelephoneNumber(), mobilePhone);
        }

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdParty.getPartyDetail().getContactPerson())) {
            failWithMessage("Expected CcdOrganisation.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();

        if (!Objects.equals(companyHouseNumber, ccdParty.getPartyDetail().getCompaniesHouseNumber())) {
            failWithMessage("Expected CcdOrganisation.companyHouseNumber to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getCompaniesHouseNumber(), companyHouseNumber);
        }

        assertThat(ccdParty.getPartyDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertIndividual(CcdApplicant ccdParty) {
        Individual actual = (Individual) this.actual;

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CcdIndividual.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdParty.getPartyDetail().getDateOfBirth())) {
            failWithMessage("Expected CcdIndividual.dateOfBirth to be <%s> but was <%s>",
                ccdParty.getPartyDetail().getDateOfBirth(), actual.getDateOfBirth());

        }
        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyDetail().getPrimaryAddress());

        assertThat(ccdParty.getPartyDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertRepresentativeDetails(Representative representative, CcdApplicant ccdParty) {
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
