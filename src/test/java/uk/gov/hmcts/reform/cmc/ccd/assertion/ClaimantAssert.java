package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CCDPartyType;
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

    public ClaimantAssert isEqualTo(CCDClaimant ccdParty) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(CCDPartyType.INDIVIDUAL, ccdParty.getPartyType())) {
                failWithMessage("Expected CCDClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CCDPartyType.INDIVIDUAL);
            }

            assertIndividual(ccdParty);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(CCDPartyType.ORGANISATION, ccdParty.getPartyType())) {
                failWithMessage("Expected CCDClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CCDPartyType.ORGANISATION);
            }

            assertOrganisation(ccdParty);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(CCDPartyType.COMPANY, ccdParty.getPartyType())) {
                failWithMessage("Expected CCDClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CCDPartyType.COMPANY);
            }

            assertCompany(ccdParty);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CCDPartyType.SOLE_TRADER, ccdParty.getPartyType())) {
                failWithMessage("Expected CCDClaimant.type to be <%s> but was <%s>",
                    ccdParty.getPartyType(), CCDPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdParty);

        }

        return this;
    }

    private void assertSoleTrader(CCDClaimant ccdParty) {
        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyAddress());

        assertThat(ccdParty.getPartyTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CCDSoleTrader.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), this.actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CCDCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        if (!Objects.equals(actual.getBusinessName(), ccdParty.getPartyBusinessName())) {
            failWithMessage("Expected CCDSoleTrader.businessName to be <%s> but was <%s>",
                ccdParty.getPartyBusinessName(), actual.getBusinessName());
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertCompany(CCDClaimant ccdParty) {
        Company actual = (Company) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getPartyAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CCDCompany.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CCDCompany.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        if (!Objects.equals(actual.getContactPerson(), ccdParty.getPartyContactPerson())) {
            failWithMessage("Expected CCDCompany.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyContactPerson(), actual.getContactPerson());
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertOrganisation(CCDClaimant ccdParty) {
        Organisation actual = (Organisation) this.actual;

        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CCDOrganisation.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdParty.getPartyPhone())) {
            failWithMessage("Expected CCDOrganisation.mobilePhone to be <%s> but was <%s>",
                ccdParty.getPartyPhone(), mobilePhone);
        }

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdParty.getPartyContactPerson())) {
            failWithMessage("Expected CCDOrganisation.contactPerson to be <%s> but was <%s>",
                ccdParty.getPartyContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();

        if (!Objects.equals(companyHouseNumber, ccdParty.getPartyCompaniesHouseNumber())) {
            failWithMessage("Expected CCDOrganisation.companyHouseNumber to be <%s> but was <%s>",
                ccdParty.getPartyCompaniesHouseNumber(), companyHouseNumber);
        }

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertIndividual(CCDClaimant ccdParty) {
        Individual actual = (Individual) this.actual;

        if (!Objects.equals(actual.getName(), ccdParty.getPartyName())) {
            failWithMessage("Expected CCDIndividual.name to be <%s> but was <%s>",
                ccdParty.getPartyName(), actual.getName());
        }

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdParty.getPartyDateOfBirth())) {
            failWithMessage("Expected CCDIndividual.dateOfBirth to be <%s> but was <%s>",
                ccdParty.getPartyDateOfBirth(), actual.getDateOfBirth());

        }
        assertThat((actual).getAddress()).isEqualTo(ccdParty.getPartyAddress());

        assertThat(ccdParty.getPartyCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertRepresentativeDetails(Representative representative, CCDClaimant ccdParty) {
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
