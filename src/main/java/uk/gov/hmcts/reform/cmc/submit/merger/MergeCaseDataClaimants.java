package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.common.AddressMapper;
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
class MergeCaseDataClaimants implements MergeCaseDataDecorator {

    private final AddressMapper addressMapper;

    @Autowired
    public MergeCaseDataClaimants(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        ccdCase.setClaimants(to(claim.getClaimants()));
    }

    private List<CcdCollectionElement<CcdClaimant>> to(List<Party> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    private CcdCollectionElement<CcdClaimant> to(Party party) {
        CcdClaimant.CcdClaimantBuilder builder = CcdClaimant.builder();

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
        return CcdCollectionElement.<CcdClaimant>builder()
            .value(builder.build())
            .id(party.getId())
            .build();
    }


    private void individual(Individual individual, CcdClaimant.CcdClaimantBuilder builder) {

        builder.partyPhone(individual.getMobilePhone());

        builder.partyDateOfBirth(individual.getDateOfBirth());
        builder.partyName(individual.getName());
        builder.partyAddress(addressMapper.to(individual.getAddress()));
        builder.partyCorrespondenceAddress(addressMapper.to(individual.getCorrespondenceAddress()));


        representative(individual.getRepresentative(), builder);

    }

    private void company(Company company, CcdClaimant.CcdClaimantBuilder builder) {

        builder.partyPhone(company.getMobilePhone());
        builder.partyContactPerson(company.getContactPerson());
        builder.partyCorrespondenceAddress(addressMapper.to(company.getCorrespondenceAddress()));
        builder.partyName(company.getName());
        builder.partyAddress(addressMapper.to(company.getAddress()));


       representative(company.getRepresentative(), builder);

    }

    private void organisation(Organisation organisation, CcdClaimant.CcdClaimantBuilder builder) {
        if (organisation == null) return;

        builder.partyCorrespondenceAddress(addressMapper.to(organisation.getCorrespondenceAddress()));

        representative(organisation.getRepresentative(), builder);

        builder.partyPhone(organisation.getMobilePhone());
        builder.partyContactPerson(organisation.getContactPerson());
        builder.partyCompaniesHouseNumber(organisation.getCompaniesHouseNumber());
        builder.partyName(organisation.getName());
        builder.partyAddress(addressMapper.to(organisation.getAddress()));

    }

    private void soleTrader(SoleTrader soleTrader, CcdClaimant.CcdClaimantBuilder builder) {

        builder.partyTitle(soleTrader.getTitle());
        builder.partyPhone(soleTrader.getMobilePhone());
        builder.partyBusinessName(soleTrader.getBusinessName());
        builder.partyCorrespondenceAddress(addressMapper.to(soleTrader.getCorrespondenceAddress()));
        builder.partyName(soleTrader.getName());
        builder.partyAddress(addressMapper.to(soleTrader.getAddress()));

        representative(soleTrader.getRepresentative(), builder);

    }

    private void representative(Representative representative, CcdClaimant.CcdClaimantBuilder builder) {
        if (representative == null) return;
        ContactDetails contactDetails = representative.getOrganisationContactDetails();

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressMapper.to(representative.getOrganisationAddress()));
    }

}
