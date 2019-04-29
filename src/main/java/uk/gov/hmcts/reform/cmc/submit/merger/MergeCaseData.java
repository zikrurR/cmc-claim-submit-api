package uk.gov.hmcts.reform.cmc.submit.merger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;

import java.util.Map;
import java.util.Objects;

@Component
public class MergeCaseData implements MergeCaseDataDecorator {

    private final ObjectMapper objectMapper;

    @Autowired
    private MergeCaseDataApplicants applicantsDecorator;
    @Autowired
    private MergeCaseDataRespondents respondentsDecorator;
    @Autowired
    private MergeCaseDataAmount amountDecorator;
    @Autowired
    private MergeCaseDataPayment paymentDecorator;
    @Autowired
    private MergeCaseDataInterest interestDecorator;
    @Autowired
    private MergeCaseDataTimeline timelineDecorator;
    @Autowired
    private MergeCaseDataEvidence evidenceDecorator;

    public MergeCaseData(ObjectMapper mergerObjectMapper) {
        this.objectMapper = mergerObjectMapper;
    }

    public Map<String, Object> merge(Map<String, Object> data, ClaimInput claim) {
        CcdCaseBuilder ccdCaseBuilder = objectMapper.convertValue(data, CcdCaseBuilder.class);

        merge(ccdCaseBuilder, claim);

        return ccdCaseBuilder.buildMap();
    }

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        Objects.requireNonNull(claim, "claimData must not be null");

        ccdCaseBuilder.externalId(Objects.toString(claim.getExternalId()));
        ccdCaseBuilder.preferredCourt(claim.getPreferredCourt());
        ccdCaseBuilder.reason(claim.getReason());

        if (claim.getStatementOfTruth() != null) {
            ccdCaseBuilder.sotSignerName(claim.getStatementOfTruth().getSignerName());
            ccdCaseBuilder.sotSignerRole(claim.getStatementOfTruth().getSignerRole());
        }

        if (claim.getPersonalInjury() != null) {
            ccdCaseBuilder.personalInjuryGeneralDamages(claim.getPersonalInjury().getGeneralDamages().name());
        }

        HousingDisrepair housingDisrepair = claim.getHousingDisrepair();
        if (housingDisrepair != null) {
            ccdCaseBuilder.housingDisrepairCostOfRepairDamages(housingDisrepair.getCostOfRepairsDamages().name());
            if (housingDisrepair.getOtherDamages() != null) {
                ccdCaseBuilder.housingDisrepairOtherDamages(housingDisrepair.getOtherDamages().name());
            }
        }

        applicantsDecorator.merge(ccdCaseBuilder, claim);

        respondentsDecorator.merge(ccdCaseBuilder, claim);

        timelineDecorator.merge(ccdCaseBuilder, claim);

        evidenceDecorator.merge(ccdCaseBuilder, claim);

        paymentDecorator.merge(ccdCaseBuilder, claim);

        interestDecorator.merge(ccdCaseBuilder, claim);

        amountDecorator.merge(ccdCaseBuilder, claim);
    }



}
