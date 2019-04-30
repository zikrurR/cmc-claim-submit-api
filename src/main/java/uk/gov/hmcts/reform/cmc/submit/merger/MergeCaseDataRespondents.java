package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdAddressBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdPartyBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdRespondentBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
class MergeCaseDataRespondents implements MergeCaseDataDecorator {

    private final AddressBuilderConverter addressBuilderConverter;

    @Autowired
    public MergeCaseDataRespondents(AddressBuilderConverter addressBuilderConverter) {
        this.addressBuilderConverter = addressBuilderConverter;
    }

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {

        ccdCaseBuilder.respondents(to(claim.getDefendants()));

    }

    private List<CcdCollectionElementBuilder<CcdRespondent>> to(List<? extends TheirDetails> theirDetails) {
        if (theirDetails == null) {
            return new ArrayList<>();
        }

        return theirDetails.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    public CcdCollectionElementBuilder<CcdRespondent> to(TheirDetails theirDetails) {
        requireNonNull(theirDetails, "theirDetails must not be null");

        CcdRespondentBuilder builder = CcdRespondentBuilder.builder();

        theirDetails(theirDetails, builder);

        return CcdCollectionElementBuilder.<CcdRespondent>builder()
            .value(builder)
            .id(theirDetails.getId());
    }

    public void theirDetails(TheirDetails theirDetails, CcdRespondentBuilder builder) {

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();

        partyDetailBuilder.emailAddress(theirDetails.getEmail());
        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(theirDetails.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(theirDetails.getServiceAddress()));

        builder.claimantProvidedDetail(partyDetailBuilder);

        representative(theirDetails.getRepresentative(), builder);

        if (theirDetails instanceof IndividualDetails) {
            IndividualDetails individual = (IndividualDetails) theirDetails;
            partyDetailBuilder.type(CcdPartyType.INDIVIDUAL);
            partyDetailBuilder.dateOfBirth(individual.getDateOfBirth());
            partyDetailBuilder.title(individual.getTitle());
            partyDetailBuilder.firstName(individual.getFirstName());
            partyDetailBuilder.lastName(individual.getLastName());

        } else if (theirDetails instanceof CompanyDetails) {
            CompanyDetails company = (CompanyDetails) theirDetails;
            partyDetailBuilder.type(CcdPartyType.COMPANY);
            partyDetailBuilder.contactPerson(company.getContactPerson());
            builder.claimantProvidedPartyName(company.getName());

        } else if (theirDetails instanceof OrganisationDetails) {
            OrganisationDetails organisation = (OrganisationDetails) theirDetails;
            partyDetailBuilder.type(CcdPartyType.ORGANISATION);
            partyDetailBuilder.contactPerson(organisation.getContactPerson());
            partyDetailBuilder.companiesHouseNumber(organisation.getCompaniesHouseNumber());
            builder.claimantProvidedPartyName(organisation.getName());

        } else if (theirDetails instanceof SoleTraderDetails) {
            SoleTraderDetails soleTrader = (SoleTraderDetails) theirDetails;
            partyDetailBuilder.type(CcdPartyType.SOLE_TRADER);
            partyDetailBuilder.businessName(soleTrader.getBusinessName());
            partyDetailBuilder.title(soleTrader.getTitle());
            partyDetailBuilder.firstName(soleTrader.getFirstName());
            partyDetailBuilder.lastName(soleTrader.getLastName());
        }
    }

    public void representative(Representative representative, CcdRespondentBuilder builder) {
        if (representative == null) {
            return;
        }

        defendantContactDetails(representative.getOrganisationContactDetails(), builder);

        builder.claimantProvidedRepresentativeOrganisationName(representative.getOrganisationName());
        CcdAddressBuilder orgAddress = addressBuilderConverter.to(representative.getOrganisationAddress());
        builder.claimantProvidedRepresentativeOrganisationAddress(orgAddress);
    }

    public void defendantContactDetails(ContactDetails contactDetails, CcdRespondentBuilder builder) {

        builder.claimantProvidedRepresentativeOrganisationEmail(contactDetails.getEmail());
        builder.claimantProvidedRepresentativeOrganisationPhone(contactDetails.getPhone());
        builder.claimantProvidedRepresentativeOrganisationDxAddress(contactDetails.getDxAddress());
    }

}
