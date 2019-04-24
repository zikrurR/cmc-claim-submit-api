package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.COMPANY;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.INDIVIDUAL;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType.ORGANISATION;

public class DefendantPartyAssert extends AbstractAssert<DefendantPartyAssert, Party> {

    public DefendantPartyAssert(Party party) {
        super(party, DefendantPartyAssert.class);
    }

    public DefendantPartyAssert isEqualTo(CcdRespondent ccdRespondent) {
        isNotNull();

        if (this.actual instanceof Individual) {
            if (!Objects.equals(INDIVIDUAL, ccdRespondent.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected ccdRespondent.partyType to be <%s> but was <%s>",
                    ccdRespondent.getClaimantProvidedDetail().getType(), INDIVIDUAL);
            }

            assertIndividual(ccdRespondent);
        }

        if (actual instanceof Organisation) {
            if (!Objects.equals(ORGANISATION, ccdRespondent.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected ccdRespondent.partyType to be <%s> but was <%s>",
                    ccdRespondent.getClaimantProvidedDetail().getType(), ORGANISATION);
            }

            assertOrganisation(ccdRespondent);
        }

        if (actual instanceof Company) {
            if (!Objects.equals(COMPANY, ccdRespondent.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected ccdRespondent.partyType to be <%s> but was <%s>",
                    ccdRespondent.getClaimantProvidedDetail().getType(), COMPANY);
            }

            assertCompany(ccdRespondent);
        }

        if (actual instanceof SoleTrader) {
            if (!Objects.equals(CcdPartyType.SOLE_TRADER, ccdRespondent.getClaimantProvidedDetail().getType())) {
                failWithMessage("Expected ccdRespondent.partyType to be <%s> but was <%s>",
                    ccdRespondent.getClaimantProvidedDetail().getType(), CcdPartyType.SOLE_TRADER);
            }
            assertSoleTrader(ccdRespondent);

        }

        return this;
    }

    private void assertParty(CcdRespondent ccdRespondent) {
        if (!Objects.equals(actual.getName(), ccdRespondent.getClaimantProvidedPartyName())) {
            failWithMessage("Expected ccdRespondent.partyName to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedPartyName(), actual.getName());
        }
        assertThat(actual.getAddress()).isEqualTo(ccdRespondent.getClaimantProvidedDetail().getPrimaryAddress());
        assertThat(ccdRespondent.getClaimantProvidedDetail().getCorrespondenceAddress()).isEqualTo(actual.getCorrespondenceAddress());

        String mobilePhone = actual.getMobilePhone();
        if (!Objects.equals(mobilePhone, ccdRespondent.getClaimantProvidedDetail().getTelephoneNumber().getTelephoneNumber())) {
            failWithMessage("Expected ccdRespondent.partyPhone to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getTelephoneNumber().getTelephoneNumber(), mobilePhone);
        }
    }

    private void assertSoleTrader(CcdRespondent ccdRespondent) {
        assertParty(ccdRespondent);

        SoleTrader actual = (SoleTrader) this.actual;
        assertThat(ccdRespondent.getClaimantProvidedDetail().getTitle()).isEqualTo(actual.getTitle());

        if (!Objects.equals(actual.getBusinessName(), ccdRespondent.getClaimantProvidedDetail().getBusinessName())) {
            failWithMessage("Expected ccdRespondent.partyBusinessName to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getBusinessName(), actual.getBusinessName());
        }
    }

    private void assertCompany(CcdRespondent ccdRespondent) {
        assertParty(ccdRespondent);
        Company actual = (Company) this.actual;

        if (!Objects.equals(actual.getContactPerson(), ccdRespondent.getClaimantProvidedDetail().getContactPerson())) {
            failWithMessage("Expected ccdRespondent.partyContactPerson to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getContactPerson(), actual.getContactPerson());
        }
    }

    private void assertOrganisation(CcdRespondent ccdRespondent) {
        assertParty(ccdRespondent);
        Organisation actual = (Organisation) this.actual;

        String contactPerson = actual.getContactPerson();
        if (!Objects.equals(contactPerson, ccdRespondent.getClaimantProvidedDetail().getContactPerson())) {
            failWithMessage("Expected ccdRespondent.partyContactPerson to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getContactPerson(), contactPerson);
        }

        String companyHouseNumber = actual.getCompaniesHouseNumber();
        if (!Objects.equals(companyHouseNumber, ccdRespondent.getClaimantProvidedDetail().getCompaniesHouseNumber())) {
            failWithMessage("Expected ccdRespondent.claimantProvidedDetail.companiesHouseNumber to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getCompaniesHouseNumber(), companyHouseNumber);
        }
    }

    private void assertIndividual(CcdRespondent ccdRespondent) {
        assertParty(ccdRespondent);
        Individual actual = (Individual) this.actual;

        if (actual.getDateOfBirth() != null
            && !Objects.equals(actual.getDateOfBirth(), ccdRespondent.getClaimantProvidedDetail().getDateOfBirth())) {
            failWithMessage("Expected ccdRespondent.claimantProvidedDetail.dateOfBirth to be <%s> but was <%s>",
                ccdRespondent.getClaimantProvidedDetail().getDateOfBirth(), actual.getDateOfBirth());
        }
    }
}
