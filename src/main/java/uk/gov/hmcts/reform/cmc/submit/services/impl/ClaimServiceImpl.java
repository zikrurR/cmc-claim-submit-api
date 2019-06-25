package uk.gov.hmcts.reform.cmc.submit.services.impl;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.converter.ClaimConverter;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;
import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException;
import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.cmc.submit.merger.MergeCaseData;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;
import uk.gov.hmcts.reform.cmc.submit.services.CoreCaseDataService;

import java.util.Map;

@Service("claimService")
public class ClaimServiceImpl implements ClaimService {


    public static final String JURISDICTION_ID = "CMC";
    public static final String CREATE_NEW_CASE = "CreateClaim";
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
    public Claim getClaim(String externalIdentifier) throws ApplicationException {
        Claim claim;
        try {
            claim = getClaimByReference(externalIdentifier);
        } catch (ApplicationException e) {
            claim = getClaimByExternalId(externalIdentifier);
        }

        return claim;
    }

    private Claim getClaimByReference(String reference) throws ApplicationException {

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(QueryBuilders.matchQuery("reference", reference));

        SearchResult result;
        try {
            result = coreCaseDataService.searchCase(searchBuilder.toString());
        } catch (Exception e) {
            throw new ApplicationException(ApplicationErrorCode.CASE_ID_DOES_NOT_EXIST);
        }

        if (result.getTotal() < 1) {
            throw new ApplicationException(ApplicationErrorCode.CASE_ID_DOES_NOT_EXIST);
        }

        if (result.getTotal() > 1) {
            throw new ApplicationException(ApplicationErrorCode.MORE_THAN_ONE_CASES_FOR_THE_REFERENCE);
        }

        return claimConverter.convert(result.getCases().get(0).getData());
    }

    private Claim getClaimByExternalId(String externalId) throws ApplicationException {

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(QueryBuilders.matchQuery("data.externalId", externalId));

        SearchResult result;
        try {
            result = coreCaseDataService.searchCase(searchBuilder.toString());
        } catch (Exception e) {
            throw new ApplicationException(ApplicationErrorCode.CASE_ID_DOES_NOT_EXIST);
        }

        if (result.getTotal() < 1) {
            throw new ApplicationException(ApplicationErrorCode.CASE_ID_DOES_NOT_EXIST);
        }

        if (result.getTotal() > 1) {
            throw new ApplicationException(ApplicationErrorCode.MORE_THAN_ONE_CASES_FOR_THE_REFERENCE);
        }
        return claimConverter.convert(result.getCases().get(0).getData());
    }


    @Override
    public ClaimOutput createNewCase(ClaimInput claimData) {

        StartEventResponse startEventResponse = coreCaseDataService.startCase();

        Map<String, Object> data = startEventResponse.getCaseDetails().getData();
        Map<String, Object> caseData = mergeCaseData.merge(data, claimData);

        CaseDetails caseDetails = coreCaseDataService.submitCase(startEventResponse, caseData);

        return extractClaimOutput(caseDetails);
    }

    private ClaimOutput extractClaimOutput(CaseDetails caseDetails) {

        ClaimOutput claimOutput = new ClaimOutput();
        claimOutput.setReferenceNumber(caseDetails.getId().toString());
        return claimOutput;
    }

}
