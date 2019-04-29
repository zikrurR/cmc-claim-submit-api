package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdApplicantBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdPartyBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdTelephoneBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class MergeCaseDataApplicants implements MergeCaseDataDecorator {

    private final AddressBuilderConverter addressBuilderConverter;

    @Autowired
    public MergeCaseDataApplicants(AddressBuilderConverter addressBuilderConverter) {
        this.addressBuilderConverter = addressBuilderConverter;
    }

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        ccdCaseBuilder.applicants(to(claim.getClaimants()));
    }

    private List<CcdCollectionElementBuilder<CcdApplicant>> to(List<Party> parties) {
        if (parties == null) {
            return new ArrayList<>();
        }


        return parties.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    private CcdCollectionElementBuilder<CcdApplicant> to(Party party) {
        CcdApplicantBuilder builder = CcdApplicantBuilder.builder();

        if (party instanceof Individual) {
            Individual individual = (Individual) party;
            individual(individual, builder);
        } else if (party instanceof Company) {
            Company company = (Company) party;
            company(company, builder);
        } else if (party instanceof Organisation) {
            Organisation organisation = (Organisation) party;
            organisation(organisation, builder);
        } else if (party instanceof SoleTrader) {
            SoleTrader soleTrader = (SoleTrader) party;
            soleTrader(soleTrader, builder);
        }
        return CcdCollectionElementBuilder.<CcdApplicant>builder()
                                            .value(builder)
                                            .id(party.getId());
    }


    private void individual(Individual individual, CcdApplicantBuilder builder) {

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();
        partyDetailBuilder.type(CcdPartyType.INDIVIDUAL);
        CcdTelephoneBuilder telephone = CcdTelephoneBuilder.builder()
                                                        .telephoneNumber(individual.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone);

        partyDetailBuilder.dateOfBirth(individual.getDateOfBirth());
        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(individual.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(individual.getCorrespondenceAddress()));

        builder.partyDetail(partyDetailBuilder);
        builder.partyName(individual.getName());

        representative(individual.getRepresentative(), builder);

    }

    private void company(Company company, CcdApplicantBuilder builder) {

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();
        partyDetailBuilder.type(CcdPartyType.COMPANY);
        CcdTelephoneBuilder telephone = CcdTelephoneBuilder.builder()
                                                        .telephoneNumber(company.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone);

        partyDetailBuilder.contactPerson(company.getContactPerson());
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(company.getCorrespondenceAddress()));
        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(company.getAddress()));

        builder.partyName(company.getName());
        builder.partyDetail(partyDetailBuilder);

        representative(company.getRepresentative(), builder);
    }

    private void organisation(Organisation organisation,CcdApplicantBuilder builder) {
        if (organisation == null) {
            return;
        }

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();
        partyDetailBuilder.type(CcdPartyType.ORGANISATION);
        CcdTelephoneBuilder telephone = CcdTelephoneBuilder.builder()
                                                        .telephoneNumber(organisation.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone);

        partyDetailBuilder.contactPerson(organisation.getContactPerson());
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(organisation.getCorrespondenceAddress()));
        partyDetailBuilder.companiesHouseNumber(organisation.getCompaniesHouseNumber());
        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(organisation.getAddress()));

        builder.partyName(organisation.getName());
        builder.partyDetail(partyDetailBuilder);

        representative(organisation.getRepresentative(), builder);
    }

    private void soleTrader(SoleTrader soleTrader, CcdApplicantBuilder builder) {

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();
        partyDetailBuilder.type(CcdPartyType.SOLE_TRADER);
        CcdTelephoneBuilder telephone = CcdTelephoneBuilder.builder()
                                                        .telephoneNumber(soleTrader.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone);

        partyDetailBuilder.title(soleTrader.getTitle());
        partyDetailBuilder.businessName(soleTrader.getBusinessName());
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(soleTrader.getCorrespondenceAddress()));
        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(soleTrader.getAddress()));

        builder.partyName(soleTrader.getName());
        builder.partyDetail(partyDetailBuilder);

        representative(soleTrader.getRepresentative(), builder);
    }

    private void representative(Representative representative, CcdApplicantBuilder builder) {
        if (representative == null) {
            return;
        }
        ContactDetails contactDetails = representative.getOrganisationContactDetails();

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressBuilderConverter.to(representative.getOrganisationAddress()));
    }

}
