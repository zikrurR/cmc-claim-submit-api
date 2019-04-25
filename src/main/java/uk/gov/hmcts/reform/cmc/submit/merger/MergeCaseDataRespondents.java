package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
class MergeCaseDataRespondents implements MergeCaseDataDecorator {

    private final AddressMapper addressMapper;

    @Autowired
    public MergeCaseDataRespondents(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {

        ccdCase.setRespondents(to(claim.getDefendants()));

    }

    private List<CcdCollectionElement<CcdRespondent>> to(List<TheirDetails> theirDetails) {
        if (theirDetails == null) {
            return new ArrayList<>();
        }

        return theirDetails.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    public CcdCollectionElement<CcdRespondent> to(TheirDetails theirDetails) {
        requireNonNull(theirDetails, "theirDetails must not be null");

        CcdRespondent.CcdRespondentBuilder builder = CcdRespondent.builder();

        theirDetails(builder, theirDetails);

        return CcdCollectionElement.<CcdRespondent>builder()
            .value(builder.build())
            .id(theirDetails.getId())
            .build();
    }

    public void theirDetails(CcdRespondent.CcdRespondentBuilder builder, TheirDetails theirDetails) {

        if (theirDetails instanceof IndividualDetails) {
            IndividualDetails individual = (IndividualDetails) theirDetails;
            individualDetails(individual, builder);
        } else if (theirDetails instanceof CompanyDetails) {
            CompanyDetails company = (CompanyDetails) theirDetails;
            companyDetails(company, builder);
        } else if (theirDetails instanceof OrganisationDetails) {
            OrganisationDetails organisation = (OrganisationDetails) theirDetails;
            organisationDetails(organisation, builder);
        } else if (theirDetails instanceof SoleTraderDetails) {
            SoleTraderDetails soleTrader = (SoleTraderDetails) theirDetails;
            soleTraderDetails(soleTrader, builder);
        }
    }

    public void individualDetails(IndividualDetails individual, CcdRespondent.CcdRespondentBuilder builder) {

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.INDIVIDUAL);

        partyDetailBuilder.dateOfBirth(individual.getDateOfBirth());
        partyDetailBuilder.emailAddress(individual.getEmail());
        partyDetailBuilder.primaryAddress(addressMapper.to(individual.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressMapper.to(individual.getServiceAddress()));

        builder.claimantProvidedDetail(partyDetailBuilder.build());
        builder.claimantProvidedPartyName(individual.getName());

        representative(individual.getRepresentative(), builder);
    }

    public void companyDetails(CompanyDetails company, CcdRespondent.CcdRespondentBuilder builder) {

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.COMPANY);

        partyDetailBuilder.emailAddress(company.getEmail());
        partyDetailBuilder.contactPerson(company.getContactPerson());
        partyDetailBuilder.primaryAddress(addressMapper.to(company.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressMapper.to(company.getServiceAddress()));

        builder.claimantProvidedDetail(partyDetailBuilder.build());
        builder.claimantProvidedPartyName(company.getName());

        representative(company.getRepresentative(), builder);
    }

    public void organisationDetails(OrganisationDetails organisation, CcdRespondent.CcdRespondentBuilder builder) {
        if (organisation == null) {
            return;
        }

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.ORGANISATION);

        partyDetailBuilder.emailAddress(organisation.getEmail());
        partyDetailBuilder.contactPerson(organisation.getContactPerson());
        partyDetailBuilder.companiesHouseNumber(organisation.getCompaniesHouseNumber());
        partyDetailBuilder.primaryAddress(addressMapper.to(organisation.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressMapper.to(organisation.getServiceAddress()));

        builder.claimantProvidedDetail(partyDetailBuilder.build());
        builder.claimantProvidedPartyName(organisation.getName());

        representative(organisation.getRepresentative(), builder);
    }

    public void soleTraderDetails(SoleTraderDetails soleTrader, CcdRespondent.CcdRespondentBuilder builder) {
        if (soleTrader == null) {
            return;
        }

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.SOLE_TRADER);

        partyDetailBuilder.emailAddress(soleTrader.getEmail());
        partyDetailBuilder.title(soleTrader.getTitle());
        partyDetailBuilder.businessName(soleTrader.getBusinessName());
        partyDetailBuilder.primaryAddress(addressMapper.to(soleTrader.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressMapper.to(soleTrader.getServiceAddress()));

        builder.claimantProvidedDetail(partyDetailBuilder.build());
        builder.claimantProvidedPartyName(soleTrader.getName());

        representative(soleTrader.getRepresentative(), builder);
    }

    public void representative(Representative representative, CcdRespondent.CcdRespondentBuilder builder) {
        if (representative == null) {
            return;
        }

        defendantContactDetails(representative.getOrganisationContactDetails(), builder);

        builder.claimantProvidedRepresentativeOrganisationName(representative.getOrganisationName());
        CcdAddress orgAddress = addressMapper.to(representative.getOrganisationAddress());
        builder.claimantProvidedRepresentativeOrganisationAddress(orgAddress);
    }

    public void defendantContactDetails(ContactDetails contactDetails, CcdRespondent.CcdRespondentBuilder builder) {

        builder.claimantProvidedRepresentativeOrganisationEmail(contactDetails.getEmail());
        builder.claimantProvidedRepresentativeOrganisationPhone(contactDetails.getPhone());
        builder.claimantProvidedRepresentativeOrganisationDxAddress(contactDetails.getDxAddress());
    }

}
