package uk.gov.hmcts.reform.cmc.submit.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.StatementOfTruth;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.PersonalInjury;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ClaimConverter {

    private final ObjectMapper objectMapper;

    private ClaimantConverter claimantConverter;
    private DefendantConverter defendantConverter;
    private AmountConverter amountConverter;
    private PaymentConverter paymentConverter;
    private InterestConverter interestConverter;
    private TimelineConverter timelineConverter;
    private EvidenceConverter evidenceConverter;

    public ClaimConverter(ObjectMapper objectMapper,
            ClaimantConverter claimantConverter,
            DefendantConverter defendantConverter,
            AmountConverter amountConverter,
            PaymentConverter paymentConverter,
            InterestConverter interestConverter,
            TimelineConverter timelineConverter,
            EvidenceConverter evidenceConverter) {
        this.objectMapper = objectMapper;

        this.claimantConverter = claimantConverter;
        this.defendantConverter = defendantConverter;
        this.amountConverter = amountConverter;
        this.paymentConverter = paymentConverter;
        this.interestConverter = interestConverter;
        this.timelineConverter = timelineConverter;
        this.evidenceConverter = evidenceConverter;
    }

    public Claim convert(Map<String, Object> caseData) {
        return from(objectMapper.convertValue(caseData, CcdCase.class));
    }

    public Claim from(CcdCase ccdCase) {

        Objects.requireNonNull(ccdCase, "ccdCase must not be null");

        Claim claim = new Claim();
        claim.setExternalId(UUID.fromString(ccdCase.getExternalId()));
        claim.setPersonalInjury(personalInjuryFrom(ccdCase));
        claim.setHousingDisrepair(housingDisrepairFrom(ccdCase));
        claim.setReason(ccdCase.getReason());
        claim.setStatementOfTruth(statementOfTruthFrom(ccdCase));
        claim.setReferenceNumber(ccdCase.getReferenceNumber());
        claim.setPreferredCourt(ccdCase.getPreferredCourt());
        claim.setTimeline(timelineConverter.from(ccdCase.getTimeline()));
        claim.setEvidences(evidenceConverter.from(ccdCase.getEvidence()));
        claim.setClaimants(claimantConverter.from(ccdCase.getApplicants()));
        claim.setDefendants(defendantConverter.from(ccdCase.getRespondents()));
        claim.setPayment(paymentConverter.from(ccdCase));
        claim.setAmount(amountConverter.from(ccdCase));
        claim.setInterest(interestConverter.from(ccdCase));

        return claim;
    }

    private PersonalInjury personalInjuryFrom(CcdCase ccdCase) {
        if (isBlank(ccdCase.getPersonalInjuryGeneralDamages())) {
            return null;
        }

        PersonalInjury personalInjury = new PersonalInjury();
        personalInjury.setGeneralDamages(DamagesExpectation.valueOf(ccdCase.getPersonalInjuryGeneralDamages()));

        return personalInjury;
    }

    private HousingDisrepair housingDisrepairFrom(CcdCase ccdCase) {
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

    private StatementOfTruth statementOfTruthFrom(CcdCase ccdCase) {
        if (isBlank(ccdCase.getSotSignerName()) && isBlank(ccdCase.getSotSignerRole())) {
            return null;
        }

        StatementOfTruth statementOfTruth = new StatementOfTruth();
        statementOfTruth.setSignerName(ccdCase.getSotSignerName());
        statementOfTruth.setSignerRole(ccdCase.getSotSignerRole());

        return statementOfTruth;
    }
}
