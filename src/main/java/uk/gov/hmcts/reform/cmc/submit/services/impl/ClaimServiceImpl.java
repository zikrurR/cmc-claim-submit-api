package uk.gov.hmcts.reform.cmc.submit.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import org.springframework.stereotype.Service;

import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.converter.ClaimConverter;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;
import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException;
import uk.gov.hmcts.reform.cmc.submit.exception.CoreCaseDataStoreException;
import uk.gov.hmcts.reform.cmc.submit.merger.MergeCaseData;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;
import uk.gov.hmcts.reform.cmc.submit.services.CoreCaseDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("claimService")
public class ClaimServiceImpl implements ClaimService {


    public static final String JURISDICTION_ID = "CMC";
    public static final String CREATE_NEW_CASE = "IssueClaim";
    public static final String CASE_TYPE_ID = "MoneyClaimCase";
    public static final String CMC_CASE_CREATE_SUMMARY = "CMC case issue";
    public static final String SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION = "Submitting CMC case issue";
    public static final String CCD_STORING_FAILURE_MESSAGE = "Failed storing claim in CCD store for case id %s on event %s";

    private final ClaimConverter claimConverter;
    private final MergeCaseData mergeCaseData;
    private final CoreCaseDataService coreCaseDataService;

    public ClaimServiceImpl(
            ClaimConverter claimConverter,
            MergeCaseData mergeCaseData,
            CoreCaseDataService coreCaseDataService) {

        this.claimConverter = claimConverter;
        this.coreCaseDataService = coreCaseDataService;
        this.mergeCaseData = mergeCaseData;
    }

    @Override
    public Claim getClaim(String externalIdentifier) throws ApplicationException  {
        Claim claim;
        try {
            claim = getClaimByReference(externalIdentifier);
        } catch (CoreCaseDataStoreException e) {
            claim = getClaimByExternalId(externalIdentifier);
        }

        return claim;
    }

    private Claim getClaimByReference(String reference) throws CoreCaseDataStoreException {

        Map<String, String> searchCriteria = new HashMap<>(ImmutableMap.of("case.referenceNumber", reference));
        List<CaseDetails> result = coreCaseDataService.searchCase(searchCriteria);

        return claimConverter.convert(result.get(0).getData());
    }

    private Claim getClaimByExternalId(String externalId) throws CoreCaseDataStoreException {

        Map<String, String> searchCriteria = new HashMap<>(ImmutableMap.of("case.externalId", externalId));
        List<CaseDetails> result = coreCaseDataService.searchCase(searchCriteria);

        return claimConverter.convert(result.get(0).getData());
    }


    @Override
    public ClaimOutput createNewCase(ClaimInput claimData) {

        StartEventResponse startEventResponse = coreCaseDataService.startCase();

        Map<String, Object> data = startEventResponse.getCaseDetails().getData();
        Map<String, JsonNode> caseData = mergeCaseData.merge(data, claimData);

        CaseDetails caseDetails = coreCaseDataService.submitCase(startEventResponse, caseData);

        return extractClaimOutput(caseDetails);
    }

    private ClaimOutput extractClaimOutput(CaseDetails caseDetails) {

        ClaimOutput claimOutput = new ClaimOutput();
        claimOutput.setReferenceNumber((String)caseDetails.getData().get("referenceNumber"));
        return claimOutput;
    }

}
