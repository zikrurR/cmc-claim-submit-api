package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdApplicant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.AddressMapper;
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
            builder.partyType(CcdPartyType.INDIVIDUAL);
            Individual individual = (Individual) party;
            individual(individual, builder);
        } else if (party instanceof Company) {
            builder.partyType(CcdPartyType.COMPANY);
            Company company = (Company) party;
            company(company, builder);
        } else if (party instanceof Organisation) {
            builder.partyType(CcdPartyType.ORGANISATION);
            Organisation organisation = (Organisation) party;
            organisation(organisation, builder);
        } else if (party instanceof SoleTrader) {
            builder.partyType(CcdPartyType.SOLE_TRADER);
            SoleTrader soleTrader = (SoleTrader) party;
            soleTrader(soleTrader, builder);
        }
        return CcdCollectionElement.<CcdApplicant>builder()
            .value(builder.build())
            .id(party.getId())
            .build();
    }


    private void individual(Individual individual, CcdApplicant.CcdApplicantBuilder builder) {

        builder.partyPhone(individual.getMobilePhone());

        builder.partyDateOfBirth(individual.getDateOfBirth());
        builder.partyName(individual.getName());
        builder.partyAddress(addressMapper.to(individual.getAddress()));
        builder.partyCorrespondenceAddress(addressMapper.to(individual.getCorrespondenceAddress()));


        representative(individual.getRepresentative(), builder);

    }

    private void company(Company company, CcdApplicant.CcdApplicantBuilder builder) {

        builder.partyPhone(company.getMobilePhone());
        builder.partyContactPerson(company.getContactPerson());
        builder.partyCorrespondenceAddress(addressMapper.to(company.getCorrespondenceAddress()));
        builder.partyName(company.getName());
        builder.partyAddress(addressMapper.to(company.getAddress()));


       representative(company.getRepresentative(), builder);

    }

    private void organisation(Organisation organisation, CcdApplicant.CcdApplicantBuilder builder) {
        if (organisation == null) return;

        builder.partyCorrespondenceAddress(addressMapper.to(organisation.getCorrespondenceAddress()));

        representative(organisation.getRepresentative(), builder);

        builder.partyPhone(organisation.getMobilePhone());
        builder.partyContactPerson(organisation.getContactPerson());
        builder.partyCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.partyName(organisation.getName());
        builder.partyAddress(addressMapper.to(organisation.getAddress()));

    }

    private void soleTrader(SoleTrader soleTrader, CcdApplicant.CcdApplicantBuilder builder) {

        builder.partyTitle(soleTrader.getTitle());
        builder.partyPhone(soleTrader.getMobilePhone());
        builder.partyBusinessName(soleTrader.getBusinessName());
        builder.partyCorrespondenceAddress(addressMapper.to(soleTrader.getCorrespondenceAddress()));
        builder.partyName(soleTrader.getName());
        builder.partyAddress(addressMapper.to(soleTrader.getAddress()));

        representative(soleTrader.getRepresentative(), builder);

    }

    private void representative(Representative representative, CcdApplicant.CcdApplicantBuilder builder) {
        if (representative == null) return;
        ContactDetails contactDetails = representative.getOrganisationContactDetails();

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

}
