package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.cmc.domain.models.StatementOfTruth;
import uk.gov.hmcts.cmc.domain.models.claimants.Party;
import uk.gov.hmcts.cmc.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.cmc.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.cmc.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.cmc.domain.models.particulars.PersonalInjury;
import uk.gov.hmcts.reform.cmc.ccd.mapper.defendant.DefendantMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ClaimMapper {

    @Autowired
    private ClaimantMapper claimantMapper;
    @Autowired
    private DefendantMapper defendantMapper;
    @Autowired
    private AmountMapper amountMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private InterestMapper interestMapper;
    @Autowired
    private TimelineMapper timelineMapper;
    @Autowired
    private EvidenceMapper evidenceMapper;


    public CCDCase to(ClaimData claim) {

        CCDCase.CCDCaseBuilder builder = CCDCase.builder();

        Objects.requireNonNull(claim, "claimData must not be null");

        builder.externalId(Objects.toString(claim.getExternalId().toString()));
        claim.getFeeCode().ifPresent(builder::feeCode);
        claim.getFeeAccountNumber().ifPresent(builder::feeAccountNumber);
        claim.getExternalReferenceNumber().ifPresent(builder::externalReferenceNumber);
        claim.getPreferredCourt().ifPresent(builder::preferredCourt);

        claim.getStatementOfTruth().ifPresent(statementOfTruth -> {
            builder.sotSignerName(statementOfTruth.getSignerName());
            builder.sotSignerRole(statementOfTruth.getSignerRole());
        });


        claim.getPersonalInjury().ifPresent(personalInjury ->
            builder.personalInjuryGeneralDamages(personalInjury.getGeneralDamages().name())
        );

        claim.getHousingDisrepair().ifPresent(housingDisrepair -> {
            builder.housingDisrepairCostOfRepairDamages(housingDisrepair.getCostOfRepairsDamages().name());
            housingDisrepair.getOtherDamages().ifPresent(damage ->
                builder.housingDisrepairOtherDamages(damage.name())
            );
        });


        builder
            .reason(claim.getReason())
            .feeAmountInPennies(claim.getFeeAmountInPennies());


        builder.claimants(claim.getClaimants().stream()
            .map(claimantMapper::to)
            .collect(Collectors.toList()));

        builder.defendants(claim.getDefendants().stream()
            .map(defendantMapper::to)
            .collect(Collectors.toList()));

        claim.getTimeline().ifPresent(timeline -> timelineMapper.to(timeline, builder));
        claim.getEvidence().ifPresent(evidence -> evidenceMapper.to(evidence, builder));

        paymentMapper.to(claim.getPayment(), builder);

        interestMapper.to(claim.getInterest(), builder);

        amountMapper.to(claim.getAmount(), builder);

        return builder.build();
    }

    public ClaimData from(CCDCase ccdCase) {

        Objects.requireNonNull(ccdCase, "ccdCase must not be null");

        List<Party> claimants = ccdCase.getClaimants()
            .stream()
            .map(claimantMapper::from)
            .collect(Collectors.toList());

//        builder
//        .id(ccdCase.getId())
//        .submitterId(ccdCase.getSubmitterId())
//        .externalId(ccdCase.getExternalId())
//        .referenceNumber(ccdCase.getReferenceNumber())
//        .createdAt(ccdCase.getSubmittedOn())
//        .issuedOn(ccdCase.getIssuedOn())
//        .submitterEmail(ccdCase.getSubmitterEmail());


        return new ClaimData(
                UUID.fromString(ccdCase.getExternalId()),
                claimants,
                getDefendants(ccdCase),
                paymentMapper.from(ccdCase),
                amountMapper.from(ccdCase),
                ccdCase.getFeeAmountInPennies(),
                interestMapper.from(ccdCase),
                personalInjuryFrom(ccdCase),
                housingDisrepairFrom(ccdCase),
                ccdCase.getReason(),
                statementOfTruthFrom(ccdCase),
                ccdCase.getFeeAccountNumber(),
                ccdCase.getExternalReferenceNumber(),
                ccdCase.getPreferredCourt(),
                ccdCase.getFeeCode(),
                timelineMapper.from(ccdCase),
                evidenceMapper.from(ccdCase)
            );
    }

    private List<TheirDetails> getDefendants(CCDCase ccdCase) {

        return ccdCase.getDefendants().stream()
            .map(defendant -> defendantMapper.from(defendant))
            .collect(Collectors.toList());
    }


    private PersonalInjury personalInjuryFrom(CCDCase ccdCase) {
        if (isBlank(ccdCase.getPersonalInjuryGeneralDamages())) {
            return null;
        }

        return new PersonalInjury(DamagesExpectation.valueOf(ccdCase.getPersonalInjuryGeneralDamages()));
    }

    private HousingDisrepair housingDisrepairFrom(CCDCase ccdCase) {
        if (isBlank(ccdCase.getHousingDisrepairCostOfRepairDamages())
            && isBlank(ccdCase.getHousingDisrepairOtherDamages())
        ) {
            return null;
        }

        DamagesExpectation costOfRepairs = DamagesExpectation.valueOf(ccdCase.getHousingDisrepairCostOfRepairDamages());
        String ccdOtherDamages = ccdCase.getHousingDisrepairOtherDamages();
        DamagesExpectation otherDamages = ccdOtherDamages != null ? DamagesExpectation.valueOf(ccdOtherDamages) : null;
        return new HousingDisrepair(costOfRepairs, otherDamages);
    }

    private StatementOfTruth statementOfTruthFrom(CCDCase ccdCase) {
        if (isBlank(ccdCase.getSotSignerName()) && isBlank(ccdCase.getSotSignerRole())) {
            return null;
        }

        return new StatementOfTruth(ccdCase.getSotSignerName(), ccdCase.getSotSignerRole());
    }
}
