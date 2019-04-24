package uk.gov.hmcts.reform.cmc.submit.merger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;

import java.util.Map;
import java.util.Objects;

@Component
public class MergeCaseData implements MergeCaseDataDecorator {

    private final ObjectMapper objectMapper;

    @Autowired
    private MergeCaseDataApplicants claimantsDecorator;
    @Autowired
    private MergeCaseDataDefendants defendantsDecorator;
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

    public Map<String, JsonNode> merge(Map<String, Object> data, ClaimInput claim) {
        CcdCase ccdCase = objectMapper.convertValue(data, CcdCase.class);

        merge(ccdCase, claim);

        return objectMapper.convertValue(ccdCase, new TypeReference<Map<String, JsonNode>>() {});
    }

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        Objects.requireNonNull(claim, "claimData must not be null");

        ccdCase.setExternalId(Objects.toString(claim.getExternalId()));
        ccdCase.setPreferredCourt(claim.getPreferredCourt());
        ccdCase.setReason(claim.getReason());

        if (claim.getStatementOfTruth() != null) {
            ccdCase.setSotSignerName(claim.getStatementOfTruth().getSignerName());
            ccdCase.setSotSignerRole(claim.getStatementOfTruth().getSignerRole());
        }

        if (claim.getPersonalInjury() != null) {
            ccdCase.setPersonalInjuryGeneralDamages(claim.getPersonalInjury().getGeneralDamages().name());
        }

        HousingDisrepair housingDisrepair = claim.getHousingDisrepair();
        if (housingDisrepair != null) {
            ccdCase.setHousingDisrepairCostOfRepairDamages(housingDisrepair.getCostOfRepairsDamages().name());
            if (housingDisrepair.getOtherDamages() != null) {
                ccdCase.setHousingDisrepairOtherDamages(housingDisrepair.getOtherDamages().name());
            }
        }

        claimantsDecorator.merge(ccdCase, claim);

        defendantsDecorator.merge(ccdCase, claim);

        timelineDecorator.merge(ccdCase, claim);

        evidenceDecorator.merge(ccdCase, claim);

        paymentDecorator.merge(ccdCase, claim);

        interestDecorator.merge(ccdCase, claim);

        amountDecorator.merge(ccdCase, claim);
    }



}
