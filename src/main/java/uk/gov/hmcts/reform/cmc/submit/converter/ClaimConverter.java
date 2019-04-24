package uk.gov.hmcts.reform.cmc.submit.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
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

    @Autowired
    private ClaimantConverter claimantConverter;
    @Autowired
    private DefendantConverter defendantConverter;
    @Autowired
    private AmountConverter amountConverter;
    @Autowired
    private PaymentConverter paymentConverter;
    @Autowired
    private InterestConverter interestConverter;
    @Autowired
    private TimelineConverter timelineConverter;
    @Autowired
    private EvidenceConverter evidenceConverter;

    public ClaimConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
