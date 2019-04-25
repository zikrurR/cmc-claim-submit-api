package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.COMPANY;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.INDIVIDUAL;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.ORGANISATION;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.SOLE_TRADER;

public class TheirDetailsAssert extends AbstractAssert<TheirDetailsAssert, TheirDetails> {

    public TheirDetailsAssert(TheirDetails party) {
        super(party, TheirDetailsAssert.class);
    }

    public TheirDetailsAssert isEqualTo(CcdRespondent ccdParty) {
        isNotNull();

        if (actual instanceof IndividualDetails) {
            if (!Objects.equals(INDIVIDUAL, ccdParty.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedDetail().getType(), INDIVIDUAL);
            }

            assertIndividualDetails(ccdParty);
        }

        if (actual instanceof OrganisationDetails) {
            if (!Objects.equals(ORGANISATION, ccdParty.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedDetail().getType(), ORGANISATION);
            }

            assertOrganisationDetails(ccdParty);
        }

        if (actual instanceof CompanyDetails) {
            if (!Objects.equals(COMPANY, ccdParty.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedDetail().getType(), COMPANY);
            }

            assertCompanyDetails(ccdParty);
        }

        if (actual instanceof SoleTraderDetails) {
            if (!Objects.equals(SOLE_TRADER, ccdParty.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected CcdDefendant.claimantProvidedType to be <%s> but was <%s>",
                    ccdParty.getClaimantProvidedDetail().getType(), SOLE_TRADER);
            }

            assertSoleTraderDetails(ccdParty);
        }

        return this;
    }

    private void assertSoleTraderDetails(CcdRespondent ccdParty) {
        SoleTraderDetails actual = (SoleTraderDetails) this.actual;
        CcdParty claimantProvidedDetail = ccdParty.getClaimantProvidedDetail();
        assertThat(actual.getAddress()).isEqualTo(claimantProvidedDetail.getPrimaryAddress());
        assertThat(claimantProvidedDetail.getTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedPartyName())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedPartyName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), claimantProvidedDetail.getEmailAddress())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                claimantProvidedDetail.getEmailAddress(), this.actual.getEmail());
        }

        if (!Objects.equals(actual.getBusinessName(),
            claimantProvidedDetail.getBusinessName())
        ) {
            failWithMessage("Expected CcdDefendant.claimantProvideBusinessName to be <%s> but was <%s>",
                claimantProvidedDetail.getBusinessName(), actual.getBusinessName());
        }

        assertThat(claimantProvidedDetail.getCorrespondenceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertCompanyDetails(CcdRespondent ccdParty) {
        CompanyDetails actual = (CompanyDetails) this.actual;

        CcdParty claimantProvidedDetail = ccdParty.getClaimantProvidedDetail();
        assertThat(actual.getAddress()).isEqualTo(claimantProvidedDetail.getPrimaryAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedPartyName())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedPartyName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), claimantProvidedDetail.getEmailAddress())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                claimantProvidedDetail.getEmailAddress(), actual.getEmail());
        }

        if (!Objects.equals(actual.getContactPerson(),
            claimantProvidedDetail.getContactPerson())
        ) {
            failWithMessage("Expected CcdDefendant.claimantProvidedContactPerson to be <%s> but was <%s>",
                claimantProvidedDetail.getContactPerson(), actual.getContactPerson());
        }

        assertThat(claimantProvidedDetail.getCorrespondenceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertOrganisationDetails(CcdRespondent ccdParty) {
        OrganisationDetails actual = (OrganisationDetails) this.actual;

        CcdParty claimantProvidedDetail = ccdParty.getClaimantProvidedDetail();
        assertThat(actual.getAddress()).isEqualTo(claimantProvidedDetail.getPrimaryAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedPartyName())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedPartyName(), this.actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), claimantProvidedDetail.getEmailAddress())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                claimantProvidedDetail.getEmailAddress(), actual.getEmail());
        }

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, claimantProvidedDetail.getContactPerson())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedContactPerson to be <%s> but was <%s>",
                claimantProvidedDetail.getContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();

        if (!Objects.equals(companyHouseNumber, claimantProvidedDetail.getCompaniesHouseNumber())) {
            failWithMessage(
                "Expected CcdDefendant.claimantProvidedCompaniesHouseNumber to be <%s> but was <%s>",
                claimantProvidedDetail.getCompaniesHouseNumber(), companyHouseNumber);
        }

        assertThat(claimantProvidedDetail.getCorrespondenceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);

    }

    private void assertIndividualDetails(CcdRespondent ccdParty) {
        IndividualDetails actual = (IndividualDetails) this.actual;

        CcdParty claimantProvidedDetail = ccdParty.getClaimantProvidedDetail();
        assertThat(actual.getAddress()).isEqualTo(claimantProvidedDetail.getPrimaryAddress());
        if (!Objects.equals(actual.getName(), ccdParty.getClaimantProvidedPartyName())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedPartyName(), actual.getName());
        }

        if (!Objects.equals(actual.getEmail(), claimantProvidedDetail.getEmailAddress())) {
            failWithMessage("Expected CcdDefendant.claimantProvidedEmail to be <%s> but was <%s>",
                claimantProvidedDetail.getEmailAddress(), actual.getEmail());
        }
        assertThat(actual.getDateOfBirth()).isEqualTo(claimantProvidedDetail.getDateOfBirth());

        assertThat(claimantProvidedDetail.getCorrespondenceAddress()).isEqualTo(actual.getServiceAddress());
        assertRepresentativeDetails(actual.getRepresentative(), ccdParty);
    }

    private void assertRepresentativeDetails(Representative representative, CcdRespondent ccdParty) {
        if (representative == null) {
            representative = new Representative();
        }


        if (!Objects.equals(representative.getOrganisationName(),
            ccdParty.getClaimantProvidedRepresentativeOrganisationName())
        ) {
            failWithMessage("Expected Representative.organisationName to be <%s> but was <%s>",
                ccdParty.getClaimantProvidedRepresentativeOrganisationName(), representative.getOrganisationName());
        }

        assertThat(representative.getOrganisationAddress())
            .isEqualTo(ccdParty.getClaimantProvidedRepresentativeOrganisationAddress());

        if (representative.getOrganisationContactDetails() != null) {
            ContactDetails contactDetails = representative.getOrganisationContactDetails();

            String claimantProvidedRepOrgDxAddress = ccdParty.getClaimantProvidedRepresentativeOrganisationDxAddress();
            if (!Objects.equals(contactDetails.getDxAddress(), claimantProvidedRepOrgDxAddress)) {
                failWithMessage("Expected Representative.organisationDxAddress to be <%s> but was <%s>",
                    claimantProvidedRepOrgDxAddress,
                    contactDetails.getDxAddress()
                );
            }

            String claimantProvidedRepOrgEmail = ccdParty.getClaimantProvidedRepresentativeOrganisationEmail();
            if (!Objects.equals(contactDetails.getEmail(), claimantProvidedRepOrgEmail)) {
                failWithMessage("Expected Representative.organisationEmail to be <%s> but was <%s>",
                    claimantProvidedRepOrgEmail, contactDetails.getEmail());
            }

            String claimantProvidedRepOrgPhone = ccdParty.getClaimantProvidedRepresentativeOrganisationPhone();
            if (!Objects.equals(contactDetails.getPhone(), claimantProvidedRepOrgPhone)) {
                failWithMessage("Expected Representative.organisationPhone to be <%s> but was <%s>",
                    claimantProvidedRepOrgPhone, contactDetails.getPhone());
            }

        }

    }
}
