package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.AmountType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CcdCaseBuilder implements Builder<CcdCase> {

    Map<String, Object> propertiesMap = new HashMap<>();

    private String externalId;
    private String referenceNumber;
    private String reason;

    private AmountType amountType;
    private BigDecimal amountLowerValue;
    private BigDecimal amountHigherValue;
    private List<CcdCollectionElementBuilder<CcdAmountRow>> amountBreakDown = new ArrayList<>();
    private BigDecimal totalAmount;

    private CcdInterestType interestType;
    private BigDecimal interestBreakDownAmount;
    private String interestBreakDownExplanation;
    private BigDecimal interestRate;
    private String interestReason;
    private BigDecimal interestSpecificDailyAmount;
    private CcdInterestDateType interestDateType;
    private LocalDate interestClaimStartDate;
    private String interestStartDateReason;
    private CcdInterestEndDateType interestEndDateType;

    private String paymentId;
    private BigDecimal paymentAmount;
    private String paymentReference;
    private String paymentStatus;
    private LocalDate paymentDateCreated;
    private String feeAccountNumber;

    private String preferredCourt;
    private String personalInjuryGeneralDamages;
    private String housingDisrepairCostOfRepairDamages;
    private String housingDisrepairOtherDamages;
    private String sotSignerName;
    private String sotSignerRole;

    private List<CcdCollectionElementBuilder<CcdApplicant>> applicants = new ArrayList<>();
    private List<CcdCollectionElementBuilder<CcdRespondent>> respondents = new ArrayList<>();
    private List<CcdCollectionElementBuilder<CcdTimelineEvent>> timeline = new ArrayList<>();
    private List<CcdCollectionElementBuilder<CcdEvidenceRow>> evidence = new ArrayList<>();

    public static CcdCaseBuilder builder() {
        return new CcdCaseBuilder();
    }

    private CcdCaseBuilder() {
    }

    @Override
    public CcdCase build() {
        CcdCase ccdCase = new CcdCase();

        ccdCase.setExternalId(externalId);
        ccdCase.setReferenceNumber(referenceNumber);
        ccdCase.setReason(reason);
        ccdCase.setAmountType(amountType);
        ccdCase.setAmountLowerValue(amountLowerValue);
        ccdCase.setAmountHigherValue(amountHigherValue);
        ccdCase.setTotalAmount(totalAmount);
        ccdCase.setInterestType(interestType);
        ccdCase.setInterestBreakDownAmount(interestBreakDownAmount);
        ccdCase.setInterestBreakDownExplanation(interestBreakDownExplanation);
        ccdCase.setInterestRate(interestRate);
        ccdCase.setInterestReason(interestReason);
        ccdCase.setInterestSpecificDailyAmount(interestSpecificDailyAmount);
        ccdCase.setInterestDateType(interestDateType);
        ccdCase.setInterestClaimStartDate(interestClaimStartDate);
        ccdCase.setInterestStartDateReason(interestStartDateReason);
        ccdCase.setInterestEndDateType(interestEndDateType);
        ccdCase.setPaymentId(paymentId);
        ccdCase.setPaymentAmount(paymentAmount);
        ccdCase.setPaymentReference(paymentReference);
        ccdCase.setPaymentStatus(paymentStatus);
        ccdCase.setPaymentDateCreated(paymentDateCreated);
        ccdCase.setFeeAccountNumber(feeAccountNumber);
        ccdCase.setPreferredCourt(preferredCourt);
        ccdCase.setPersonalInjuryGeneralDamages(personalInjuryGeneralDamages);
        ccdCase.setHousingDisrepairCostOfRepairDamages(housingDisrepairCostOfRepairDamages);
        ccdCase.setHousingDisrepairOtherDamages(housingDisrepairOtherDamages);
        ccdCase.setSotSignerName(sotSignerName);
        ccdCase.setSotSignerRole(sotSignerRole);

        if (amountBreakDown != null) {
            ccdCase.setAmountBreakDown(listBuilderForList(amountBreakDown));
        }
        if (applicants != null) {
            ccdCase.setApplicants(listBuilderForList(applicants));
        }
        if (respondents != null) {
            ccdCase.setRespondents(listBuilderForList(respondents));
        }
        if (timeline != null) {
            ccdCase.setTimeline(listBuilderForList(timeline));
        }
        if (evidence != null) {
            ccdCase.setEvidence(listBuilderForList(evidence));
        }

        return ccdCase;
    }

    private static <T extends CcdCollectionElementBuilder<R>, R> List<CcdCollectionElement<R>> listBuilderForList(List<T> listToBuild) {
        return listToBuild.stream()
                          .map(x -> x.build())
                          .collect(Collectors.toList());
    }

    private static <T extends CcdCollectionElementBuilder<R>, R> List<Map<String, Object>> listBuilderForMap(List<T> listToBuild) {
        return listToBuild.stream()
                          .map(x -> x.buildMap())
                          .collect(Collectors.toList());
    }

    public Map<String, Object> buildMap() {

        HashMap<String, Object> hashMap = new HashMap<>(propertiesMap);

        if (amountBreakDown != null) {
            hashMap.put("amountBreakDown", listBuilderForMap(amountBreakDown));
        }
        if (applicants != null) {
            hashMap.put("applicants", listBuilderForMap(applicants));
        }
        if (respondents != null) {
            hashMap.put("respondents", listBuilderForMap(respondents));
        }
        if (timeline != null) {
            hashMap.put("timeline", listBuilderForMap(timeline));
        }
        if (evidence != null) {
            hashMap.put("evidence", listBuilderForMap(evidence));
        }
        return hashMap;
    }

    public CcdCaseBuilder externalId(String externalId) {
        this.externalId = externalId;
        propertiesMap.put("externalId", externalId);
        return this;
    }

    public CcdCaseBuilder referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        propertiesMap.put("referenceNumber", referenceNumber);
        return this;
    }

    public CcdCaseBuilder reason(String reason) {
        this.reason = reason;
        propertiesMap.put("reason", reason);
        return this;
    }

    public CcdCaseBuilder amountType(AmountType amountType) {
        this.amountType = amountType;
        propertiesMap.put("amountType", amountType);
        return this;
    }

    public CcdCaseBuilder amountLowerValue(BigDecimal amountLowerValue) {
        this.amountLowerValue = amountLowerValue;
        propertiesMap.put("amountLowerValue", amountLowerValue);
        return this;
    }

    public CcdCaseBuilder amountHigherValue(BigDecimal amountHigherValue) {
        this.amountHigherValue = amountHigherValue;
        propertiesMap.put("amountHigherValue", amountHigherValue);
        return this;
    }

    public CcdCaseBuilder amountBreakDown(List<CcdCollectionElementBuilder<CcdAmountRow>> amountBreakDown) {
        this.amountBreakDown = amountBreakDown;
        return this;
    }

    public CcdCaseBuilder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        propertiesMap.put("totalAmount", totalAmount);
        return this;
    }

    public CcdCaseBuilder interestType(CcdInterestType interestType) {
        this.interestType = interestType;
        propertiesMap.put("interestType", interestType);
        return this;
    }

    public CcdCaseBuilder interestBreakDownAmount(BigDecimal interestBreakDownAmount) {
        this.interestBreakDownAmount = interestBreakDownAmount;
        propertiesMap.put("interestBreakDownAmount", interestBreakDownAmount);
        return this;
    }

    public CcdCaseBuilder interestBreakDownExplanation(String interestBreakDownExplanation) {
        this.interestBreakDownExplanation = interestBreakDownExplanation;
        propertiesMap.put("interestBreakDownExplanation", interestBreakDownExplanation);
        return this;
    }

    public CcdCaseBuilder interestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        propertiesMap.put("interestRate", interestRate);
        return this;
    }

    public CcdCaseBuilder interestReason(String interestReason) {
        this.interestReason = interestReason;
        propertiesMap.put("interestReason", interestReason);
        return this;
    }

    public CcdCaseBuilder interestSpecificDailyAmount(BigDecimal interestSpecificDailyAmount) {
        this.interestSpecificDailyAmount = interestSpecificDailyAmount;
        propertiesMap.put("interestSpecificDailyAmount", interestSpecificDailyAmount);
        return this;
    }

    public CcdCaseBuilder interestDateType(CcdInterestDateType interestDateType) {
        this.interestDateType = interestDateType;
        propertiesMap.put("interestDateType", interestDateType);
        return this;
    }

    public CcdCaseBuilder interestClaimStartDate(LocalDate interestClaimStartDate) {
        this.interestClaimStartDate = interestClaimStartDate;
        propertiesMap.put("interestClaimStartDate", interestClaimStartDate);
        return this;
    }

    public CcdCaseBuilder interestStartDateReason(String interestStartDateReason) {
        this.interestStartDateReason = interestStartDateReason;
        propertiesMap.put("interestStartDateReason", interestStartDateReason);
        return this;
    }

    public CcdCaseBuilder interestEndDateType(CcdInterestEndDateType interestEndDateType) {
        this.interestEndDateType = interestEndDateType;
        propertiesMap.put("interestEndDateType", interestEndDateType);
        return this;
    }

    public CcdCaseBuilder paymentId(String paymentId) {
        this.paymentId = paymentId;
        propertiesMap.put("paymentId", paymentId);
        return this;
    }

    public CcdCaseBuilder paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        propertiesMap.put("paymentAmount", paymentAmount);
        return this;
    }

    public CcdCaseBuilder paymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
        propertiesMap.put("paymentReference", paymentReference);
        return this;
    }

    public CcdCaseBuilder paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        propertiesMap.put("paymentStatus", paymentStatus);
        return this;
    }

    public CcdCaseBuilder paymentDateCreated(LocalDate paymentDateCreated) {
        this.paymentDateCreated = paymentDateCreated;
        propertiesMap.put("paymentDateCreated", paymentDateCreated);
        return this;
    }

    public CcdCaseBuilder feeAccountNumber(String feeAccountNumber) {
        this.feeAccountNumber = feeAccountNumber;
        propertiesMap.put("feeAccountNumber", feeAccountNumber);
        return this;
    }

    public CcdCaseBuilder preferredCourt(String preferredCourt) {
        this.preferredCourt = preferredCourt;
        propertiesMap.put("preferredCourt", preferredCourt);
        return this;
    }

    public CcdCaseBuilder personalInjuryGeneralDamages(String personalInjuryGeneralDamages) {
        this.personalInjuryGeneralDamages = personalInjuryGeneralDamages;
        propertiesMap.put("personalInjuryGeneralDamages", personalInjuryGeneralDamages);
        return this;
    }

    public CcdCaseBuilder housingDisrepairCostOfRepairDamages(String housingDisrepairCostOfRepairDamages) {
        this.housingDisrepairCostOfRepairDamages = housingDisrepairCostOfRepairDamages;
        propertiesMap.put("housingDisrepairCostOfRepairDamages", housingDisrepairCostOfRepairDamages);
        return this;
    }

    public CcdCaseBuilder housingDisrepairOtherDamages(String housingDisrepairOtherDamages) {
        this.housingDisrepairOtherDamages = housingDisrepairOtherDamages;
        propertiesMap.put("housingDisrepairOtherDamages", housingDisrepairOtherDamages);
        return this;
    }

    public CcdCaseBuilder sotSignerName(String sotSignerName) {
        this.sotSignerName = sotSignerName;
        propertiesMap.put("sotSignerName", sotSignerName);
        return this;
    }

    public CcdCaseBuilder sotSignerRole(String sotSignerRole) {
        this.sotSignerRole = sotSignerRole;
        propertiesMap.put("sotSignerRole", sotSignerRole);
        return this;
    }

    public CcdCaseBuilder applicants(List<CcdCollectionElementBuilder<CcdApplicant>> applicants) {
        this.applicants = applicants;
        return this;
    }

    public CcdCaseBuilder respondents(List<CcdCollectionElementBuilder<CcdRespondent>> respondents) {
        this.respondents = respondents;
        return this;
    }

    public CcdCaseBuilder timeline(List<CcdCollectionElementBuilder<CcdTimelineEvent>> timeline) {
        this.timeline = timeline;
        return this;
    }

    public CcdCaseBuilder evidence(List<CcdCollectionElementBuilder<CcdEvidenceRow>> evidence) {
        this.evidence = evidence;
        return this;
    }

}
