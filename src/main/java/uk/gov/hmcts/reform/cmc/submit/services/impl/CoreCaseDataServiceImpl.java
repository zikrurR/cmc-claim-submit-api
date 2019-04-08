package uk.gov.hmcts.reform.cmc.submit.services.impl;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.services.CoreCaseDataService;

import java.util.List;
import java.util.Map;

@Service
public class CoreCaseDataServiceImpl implements CoreCaseDataService {

    public static final String JURISDICTION_ID = "CMC";
    public static final String CREATE_NEW_CASE = "IssueClaim";
    public static final String CASE_TYPE_ID = "MoneyClaimCase";
    public static final String CMC_CASE_CREATE_SUMMARY = "CMC case issue";
    public static final String SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION = "Submitting CMC case issue";
    public static final String  AUTHORISATION_HEADER_NAME = "authorisation";

    private final CoreCaseDataApi coreCaseDataApi;
    private final AuthTokenGenerator authTokenGenerator;

    @Autowired private WebRequest webRequest;

    public CoreCaseDataServiceImpl(
            CoreCaseDataApi coreCaseDataApi,
            AuthTokenGenerator authTokenGenerator) {

        this.coreCaseDataApi = coreCaseDataApi;
        this.authTokenGenerator = authTokenGenerator;
    }

    @Override
    public CaseDetails submitCase(StartEventResponse startEventResponse, Map<String, JsonNode> ccdCase) {

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder()
                    .id(startEventResponse.getEventId())
                    .summary(CMC_CASE_CREATE_SUMMARY)
                    .description(SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION)
                    .build())
                .data(ccdCase)
                .build();


        String idamId = "1"; // user.getUserDetails().getId();
        return coreCaseDataApi.submitForCitizen(
            webRequest.getHeader(AUTHORISATION_HEADER_NAME),
            this.authTokenGenerator.generate(),
            idamId,
            JURISDICTION_ID,
            CASE_TYPE_ID,
            true,
            caseDataContent
        );
    }

    @Override
    public StartEventResponse startCase() {

        String idamId = "12"; // user.getUserDetails().getId();
        return coreCaseDataApi.startForCitizen(
            webRequest.getHeader(AUTHORISATION_HEADER_NAME),
            authTokenGenerator.generate(),
            idamId,
            JURISDICTION_ID,
            CASE_TYPE_ID,
            CREATE_NEW_CASE
        );
    }

    @Override
    public List<CaseDetails> searchCase(Map<String, String> searchCriteria) {

        String idamId = "123"; // user.getUserDetails().getId();
        return coreCaseDataApi.searchForCitizen(
            webRequest.getHeader(AUTHORISATION_HEADER_NAME),
            authTokenGenerator.generate(),
            idamId,
            JURISDICTION_ID,
            CASE_TYPE_ID,
            searchCriteria
        );
    }

}
