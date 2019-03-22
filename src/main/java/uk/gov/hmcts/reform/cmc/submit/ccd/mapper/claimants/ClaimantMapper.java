package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CCDPartyType;
import uk.gov.hmcts.cmc.domain.models.claimants.Company;
import uk.gov.hmcts.cmc.domain.models.claimants.Individual;
import uk.gov.hmcts.cmc.domain.models.claimants.Organisation;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.cmc.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.exception.MappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClaimantMapper implements Mapper<List<CCDCollectionElement<CCDClaimant>>, List<Party>> {

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
    public List<CCDCollectionElement<CCDClaimant>> to(List<Party> evidences) {
        if (evidences == null) return new ArrayList<>();


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Party> from(List<CCDCollectionElement<CCDClaimant>> ccdEvidence) {
        if (ccdEvidence == null) return new ArrayList<>();

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }



    public CCDCollectionElement<CCDClaimant> to(Party party) {
        CCDClaimant.CCDClaimantBuilder builder = CCDClaimant.builder();

        if (party instanceof Individual) {
            builder.partyType(CCDPartyType.INDIVIDUAL);
            Individual individual = (Individual) party;
            individualMapper.to(individual, builder);
        } else if (party instanceof Company) {
            builder.partyType(CCDPartyType.COMPANY);
            Company company = (Company) party;
            companyMapper.to(company, builder);
        } else if (party instanceof Organisation) {
            builder.partyType(CCDPartyType.ORGANISATION);
            Organisation organisation = (Organisation) party;
            organisationMapper.to(organisation, builder);
        } else if (party instanceof SoleTrader) {
            builder.partyType(CCDPartyType.SOLE_TRADER);
            SoleTrader soleTrader = (SoleTrader) party;
            soleTraderMapper.to(soleTrader, builder);
        }
        return CCDCollectionElement.<CCDClaimant>builder()
            .value(builder.build())
            .id(party.getId())
            .build();
    }

    public Party from(CCDCollectionElement<CCDClaimant> ccdClaimant) {
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
