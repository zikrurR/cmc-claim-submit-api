package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.defendant.CCDDefendant;
import uk.gov.hmcts.cmc.domain.models.common.ContactDetails;
import uk.gov.hmcts.cmc.domain.models.common.Representative;
import uk.gov.hmcts.cmc.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.cmc.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.cmc.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.cmc.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.COMPANY;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.INDIVIDUAL;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.ORGANISATION;
import static uk.gov.hmcts.cmc.ccd.domain.CCDPartyType.SOLE_TRADER;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class TheirDetailsAssert extends AbstractAssert<TheirDetailsAssert, TheirDetails> {

    public TheirDetailsAssert(TheirDetails party) {
        super(party, TheirDetailsAssert.class);
    }

    public TheirDetailsAssert isEqualTo(CCDDefendant ccdParty) {
        isNotNull();

        if (actual instanceof IndividualDetails) {
            if (!Objects.equals(INDIVIDUAL, ccdParty.getClaimantProvidedType())) {
                failWithMessage("Expected CCDDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedType(), INDIVIDUAL);
            }

            assertIndividualDetails(ccdParty);
        }

        if (actual instanceof OrganisationDetails) {
            if (!Objects.equals(ORGANISATION, ccdParty.getClaimantProvidedType())) {
                failWithMessage("Expected CCDDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedType(), ORGANISATION);
            }

            assertOrganisationDetails(ccdParty);
        }

        if (actual instanceof CompanyDetails) {
            if (!Objects.equals(COMPANY, ccdParty.getClaimantProvidedType())) {
                failWithMessage("Expected CCDDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedType(), COMPANY);
            }

            assertCompanyDetails(ccdParty);
        }

        if (actual instanceof SoleTraderDetails) {
            if (!Objects.equals(SOLE_TRADER, ccdParty.getClaimantProvidedType())) {
                failWithMessage("Expected CCDDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedType(), SOLE_TRADER);
            }

            assertSoleTraderDetails(ccdParty);
        }

        return this;
    }

    private void assertSoleTraderDetails(CCDDefendant ccdParty) {
        SoleTraderDetails actual = (SoleTraderDetails) this.actual;
        assertThat(actual.getAddress()).isEqualTo(ccdParty.getClaimantProvidedAddress());
        assertThat(ccdParty.getClaimantProvidedTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedName())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), ccdParty.getClaimantProvidedEmail())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedEmail(), this.actual.getEmail());
        }

        if (!Objects.equals(actual.getBusinessName(),
            ccdParty.getClaimantProvidedBusinessName())
        ) {
            failWithMessage("Expected CCDDefendant.claimantProvideBusinessName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedBusinessName(), actual.getBusinessName());
        }

        assertThat(ccdParty.getClaimantProvidedServiceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertCompanyDetails(CCDDefendant ccdParty) {
        CompanyDetails actual = (CompanyDetails) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getClaimantProvidedAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedName())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), ccdParty.getClaimantProvidedEmail())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedEmail(), actual.getEmail());
        }

        if (!Objects.equals(actual.getContactPerson(),
            ccdParty.getClaimantProvidedContactPerson())
        ) {
            failWithMessage("Expected CCDDefendant.claimantProvidedContactPerson to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedContactPerson(), actual.getContactPerson());
        }

        assertThat(ccdParty.getClaimantProvidedServiceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertOrganisationDetails(CCDDefendant ccdParty) {
        OrganisationDetails actual = (OrganisationDetails) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getClaimantProvidedAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedName())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), ccdParty.getClaimantProvidedEmail())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedEmail(), actual.getEmail());
        }

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdParty.getClaimantProvidedContactPerson())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedContactPerson to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();

        if (!Objects.equals(companyHouseNumber, ccdParty.getClaimantProvidedCompaniesHouseNumber())) {
            failWithMessage(
                "Expected CCDDefendant.claimantProvidedCompaniesHouseNumber to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedCompaniesHouseNumber(), companyHouseNumber);
        }

        assertThat(ccdParty.getClaimantProvidedServiceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertIndividualDetails(CCDDefendant ccdParty) {
        IndividualDetails actual = (IndividualDetails) this.actual;

        assertThat(actual.getAddress()).isEqualTo(ccdParty.getClaimantProvidedAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedName())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedName(), actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), ccdParty.getClaimantProvidedEmail())) {
            failWithMessage("Expected CCDDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedEmail(), actual.getEmail());
        }
        assertThat(actual.getDateOfBirth()).isEqualTo(ccdParty.getClaimantProvidedDateOfBirth());

        assertThat(ccdParty.getClaimantProvidedServiceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);
    }

    private void assertRepresentativeDetails(Representative representative, CCDDefendant ccdParty) {
        if (representative == null) representative = new Representative();


        if (!Objects.equals(representative.getOrganisationName(),
            ccdParty.getClaimantProvidedRepresentativeOrganisationName())
        ) {
            failWithMessage("Expected Representative.organisationName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedRepresentativeOrganisationName(), representative.getOrganisationName());
        }

        assertThat(representative.getOrganisationAddress())
            .isEqualTo(ccdParty.getClaimantProvidedRepresentativeOrganisationAddress());

        if(representative.getOrganisationContactDetails()!= null) {
            ContactDetails contactDetails = representative.getOrganisationContactDetails();

            if (!Objects.equals(contactDetails.getDxAddress(), ccdParty.getClaimantProvidedRepresentativeOrganisationDxAddress())) {
                failWithMessage("Expected Representative.organisationDxAddress to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedRepresentativeOrganisationDxAddress(),
                    contactDetails.getDxAddress()
                );
            }



            if (!Objects.equals(contactDetails.getEmail(), ccdParty.getClaimantProvidedRepresentativeOrganisationEmail())) {
                failWithMessage("Expected Representative.organisationEmail to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedRepresentativeOrganisationEmail(), contactDetails.getEmail());
            }

            if (!Objects.equals(contactDetails.getPhone(), ccdParty.getClaimantProvidedRepresentativeOrganisationPhone())) {
                failWithMessage("Expected Representative.organisationPhone to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedRepresentativeOrganisationPhone(), contactDetails.getPhone());
            }

        }

    }
}
