package uk.gov.hmcts.reform.cmc.submit.ccd.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.cmc.domain.models.StatementOfTruth;
import uk.gov.hmcts.cmc.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.cmc.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.cmc.domain.models.particulars.PersonalInjury;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount.AmountMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.claimants.ClaimantMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.defendants.DefendantMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.evidence.EvidenceMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest.InterestMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.payment.PaymentMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.timeline.TimelineMapper;

import java.util.Objects;
import java.util.UUID;

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
        Objects.requireNonNull(claim, "claimData must not be null");

        CCDCase.CCDCaseBuilder builder = CCDCase.builder();


        builder.externalId(Objects.toString(claim.getExternalId()));
        builder.feeAccountNumber(claim.getFeeAccountNumber());

        builder.externalReferenceNumber(claim.getExternalReferenceNumber());
        builder.preferredCourt(claim.getPreferredCourt());
        builder.reason(claim.getReason());


        if (claim.getStatementOfTruth() != null) {
            builder.sotSignerName(claim.getStatementOfTruth().getSignerName());
            builder.sotSignerRole(claim.getStatementOfTruth().getSignerRole());
        }


        if (claim.getPersonalInjury() != null) {
            builder.personalInjuryGeneralDamages(claim.getPersonalInjury().getGeneralDamages().name());
        }

        HousingDisrepair housingDisrepair = claim.getHousingDisrepair();
        if (housingDisrepair != null) {
            builder.housingDisrepairCostOfRepairDamages(housingDisrepair.getCostOfRepairsDamages().name());
            if (housingDisrepair.getOtherDamages() != null) {
                builder.housingDisrepairOtherDamages(housingDisrepair.getOtherDamages().name());
            }
        }



        builder.claimants(claimantMapper.to(claim.getClaimants()));

        builder.defendants(defendantMapper.to(claim.getDefendants()));


        builder.timeline(timelineMapper.to(claim.getTimeline()));

        builder.evidence(evidenceMapper.to(claim.getEvidences()));

        paymentMapper.to(claim.getPayment(), builder);

        interestMapper.to(claim.getInterest(), builder);

        amountMapper.to(claim.getAmount(), builder);

        return builder.build();
    }

    public ClaimData from(CCDCase ccdCase) {

        Objects.requireNonNull(ccdCase, "ccdCase must not be null");

//        builder
//        .id(ccdCase.getId())
//        .submitterId(ccdCase.getSubmitterId())
//        .externalId(ccdCase.getExternalId())
//        .referenceNumber(ccdCase.getReferenceNumber())
//        .createdAt(ccdCase.getSubmittedOn())
//        .issuedOn(ccdCase.getIssuedOn())
//        .submitterEmail(ccdCase.getSubmitterEmail());


        ClaimData claim = new ClaimData();
        claim.setExternalId(UUID.fromString(ccdCase.getExternalId()));
        claim.setPersonalInjury(personalInjuryFrom(ccdCase));
        claim.setHousingDisrepair(housingDisrepairFrom(ccdCase));
        claim.setReason(ccdCase.getReason());
        claim.setStatementOfTruth(statementOfTruthFrom(ccdCase));
        claim.setFeeAccountNumber(ccdCase.getFeeAccountNumber());
        claim.setExternalReferenceNumber(ccdCase.getExternalReferenceNumber());
        claim.setPreferredCourt(ccdCase.getPreferredCourt());
        claim.setTimeline(timelineMapper.from(ccdCase.getTimeline()));
        claim.setEvidences(evidenceMapper.from(ccdCase.getEvidence()));
        claim.setClaimants(claimantMapper.from(ccdCase.getClaimants()));
        claim.setDefendants(defendantMapper.from(ccdCase.getDefendants()));
        claim.setPayment(paymentMapper.from(ccdCase));
        claim.setAmount(amountMapper.from(ccdCase));
        claim.setInterest(interestMapper.from(ccdCase));

        return claim;
    }

    private PersonalInjury personalInjuryFrom(CCDCase ccdCase) {
        if (isBlank(ccdCase.getPersonalInjuryGeneralDamages())) {
            return null;
        }

        PersonalInjury personalInjury = new PersonalInjury();
        personalInjury.setGeneralDamages(DamagesExpectation.valueOf(ccdCase.getPersonalInjuryGeneralDamages()));

        return personalInjury;
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

        HousingDisrepair housingDisrepair = new HousingDisrepair();
        housingDisrepair.setCostOfRepairsDamages(costOfRepairs);
        housingDisrepair.setOtherDamages(otherDamages);
        return housingDisrepair;
    }

    private StatementOfTruth statementOfTruthFrom(CCDCase ccdCase) {
        if (isBlank(ccdCase.getSotSignerName()) && isBlank(ccdCase.getSotSignerRole())) {
            return null;
        }

        StatementOfTruth statementOfTruth = new StatementOfTruth();
        statementOfTruth.setSignerName(ccdCase.getSotSignerName());
        statementOfTruth.setSignerRole(ccdCase.getSotSignerRole());

        return statementOfTruth;
    }
}
