package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTelephone;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class MergeCaseDataApplicants implements MergeCaseDataDecorator {

    private final AddressMapper addressMapper;

    @Autowired
    public MergeCaseDataApplicants(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        ccdCase.setApplicants(to(claim.getClaimants()));
    }

    private List<CcdCollectionElement<CcdApplicant>> to(List<Party> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    private CcdCollectionElement<CcdApplicant> to(Party party) {
        CcdApplicant.CcdApplicantBuilder builder = CcdApplicant.builder();

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
        return CcdCollectionElement.<CcdApplicant>builder()
            .value(builder.build())
            .id(party.getId())
            .build();
    }


    private void individual(Individual individual, CcdApplicant.CcdApplicantBuilder builder) {

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.INDIVIDUAL);
        CcdTelephone.CcdTelephoneBuilder telephone = CcdTelephone.builder()
                                                        .telephoneNumber(individual.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone.build());

        partyDetailBuilder.dateOfBirth(individual.getDateOfBirth());
        partyDetailBuilder.primaryAddress(addressMapper.to(individual.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressMapper.to(individual.getCorrespondenceAddress()));

        builder.partyDetail(partyDetailBuilder.build());
        builder.partyName(individual.getName());

        representative(individual.getRepresentative(), builder);

    }

    private void company(Company company, CcdApplicant.CcdApplicantBuilder builder) {

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.COMPANY);
        CcdTelephone.CcdTelephoneBuilder telephone = CcdTelephone.builder()
                                                        .telephoneNumber(company.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone.build());

        partyDetailBuilder.contactPerson(company.getContactPerson());
        partyDetailBuilder.correspondenceAddress(addressMapper.to(company.getCorrespondenceAddress()));
        partyDetailBuilder.primaryAddress(addressMapper.to(company.getAddress()));

        builder.partyName(company.getName());
        builder.partyDetail(partyDetailBuilder.build());

        representative(company.getRepresentative(), builder);
    }

    private void organisation(Organisation organisation, CcdApplicant.CcdApplicantBuilder builder) {
        if (organisation == null) {
            return;
        }

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.ORGANISATION);
        CcdTelephone.CcdTelephoneBuilder telephone = CcdTelephone.builder()
                                                        .telephoneNumber(organisation.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone.build());

        partyDetailBuilder.contactPerson(organisation.getContactPerson());
        partyDetailBuilder.correspondenceAddress(addressMapper.to(organisation.getCorrespondenceAddress()));
        partyDetailBuilder.companiesHouseNumber(organisation.getCompaniesHouseNumber());
        partyDetailBuilder.primaryAddress(addressMapper.to(organisation.getAddress()));

        builder.partyName(organisation.getName());
        builder.partyDetail(partyDetailBuilder.build());

        representative(organisation.getRepresentative(), builder);
    }

    private void soleTrader(SoleTrader soleTrader, CcdApplicant.CcdApplicantBuilder builder) {

        CcdParty.CcdPartyBuilder partyDetailBuilder = CcdParty.builder();
        partyDetailBuilder.type(CcdPartyType.SOLE_TRADER);
        CcdTelephone.CcdTelephoneBuilder telephone = CcdTelephone.builder()
                                                        .telephoneNumber(soleTrader.getMobilePhone());

        partyDetailBuilder.telephoneNumber(telephone.build());

        partyDetailBuilder.title(soleTrader.getTitle());
        partyDetailBuilder.businessName(soleTrader.getBusinessName());
        partyDetailBuilder.correspondenceAddress(addressMapper.to(soleTrader.getCorrespondenceAddress()));
        partyDetailBuilder.primaryAddress(addressMapper.to(soleTrader.getAddress()));

        builder.partyName(soleTrader.getName());
        builder.partyDetail(partyDetailBuilder.build());

        representative(soleTrader.getRepresentative(), builder);
    }

    private void representative(Representative representative, CcdApplicant.CcdApplicantBuilder builder) {
        if (representative == null) {
            return;
        }
        ContactDetails contactDetails = representative.getOrganisationContactDetails();

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

}
