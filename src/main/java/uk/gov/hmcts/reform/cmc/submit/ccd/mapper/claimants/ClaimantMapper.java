package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.exception.MappingException;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClaimantMapper implements Mapper<List<CcdCollectionElement<CcdClaimant>>, List<Party>> {

    private final IndividualMapper individualMapper;
    private final CompanyMapper companyMapper;
    private final OrganisationMapper organisationMapper;
    private final SoleTraderMapper soleTraderMapper;

    public ClaimantMapper(IndividualMapper individualMapper,
                          CompanyMapper companyMapper,
                          OrganisationMapper organisationMapper,
                          SoleTraderMapper soleTraderMapper) {

        this.individualMapper = individualMapper;
        this.companyMapper = companyMapper;
        this.organisationMapper = organisationMapper;
        this.soleTraderMapper = soleTraderMapper;
    }

    @Override
    public List<CcdCollectionElement<CcdClaimant>> to(List<Party> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Party> from(List<CcdCollectionElement<CcdClaimant>> ccdEvidence) {
        if (ccdEvidence == null) {
            return new ArrayList<>();
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }

    private CcdCollectionElement<CcdClaimant> to(Party party) {
        CcdClaimant.CcdClaimantBuilder builder = CcdClaimant.builder();

        if (party instanceof Individual) {
            builder.partyType(CcdPartyType.INDIVIDUAL);
            Individual individual = (Individual) party;
            individualMapper.to(individual, builder);
        } else if (party instanceof Company) {
            builder.partyType(CcdPartyType.COMPANY);
            Company company = (Company) party;
            companyMapper.to(company, builder);
        } else if (party instanceof Organisation) {
            builder.partyType(CcdPartyType.ORGANISATION);
            Organisation organisation = (Organisation) party;
            organisationMapper.to(organisation, builder);
        } else if (party instanceof SoleTrader) {
            builder.partyType(CcdPartyType.SOLE_TRADER);
            SoleTrader soleTrader = (SoleTrader) party;
            soleTraderMapper.to(soleTrader, builder);
        }
        return CcdCollectionElement.<CcdClaimant>builder()
            .value(builder.build())
            .id(party.getId())
            .build();
    }

    private Party from(CcdCollectionElement<CcdClaimant> ccdClaimant) {
        switch (ccdClaimant.getValue().getPartyType()) {
            case COMPANY:
                return companyMapper.from(ccdClaimant);
            case INDIVIDUAL:
                return individualMapper.from(ccdClaimant);
            case SOLE_TRADER:
                return soleTraderMapper.from(ccdClaimant);
            case ORGANISATION:
                return organisationMapper.from(ccdClaimant);
            default:
                throw new MappingException("Invalid claimant type, " + ccdClaimant.getValue().getPartyType());
        }
    }
}
