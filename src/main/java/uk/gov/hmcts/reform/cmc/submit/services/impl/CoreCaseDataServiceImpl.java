package uk.gov.hmcts.reform.cmc.submit.services.impl;

import com.fasterxml.jackson.databind.JsonNode;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.services.CoreCaseDataService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service
public class CoreCaseDataServiceImpl implements CoreCaseDataService {

    public static final String JURISDICTION_ID = "CMC";
    public static final String CREATE_NEW_CASE = "IssueClaim";
    public static final String CASE_TYPE_ID = "MoneyClaimCase";
    public static final String CMC_CASE_CREATE_SUMMARY = "CMC case issue";
    public static final String SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION = "Submitting CMC case issue";

    private final CoreCaseDataApi coreCaseDataApi;
    private final AuthTokenGenerator authTokenGenerator;

	@Autowired
    private HttpServletRequest request;

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

        return coreCaseDataApi.submitForCitizen(
            getAuthorisationHeader(),
            this.authTokenGenerator.generate(),
            getIdamIdFormCcdToken(caseDataContent.getEventToken()),
            JURISDICTION_ID,
            CASE_TYPE_ID,
            true,
            caseDataContent
        );
    }


    @Override
    public StartEventResponse startCase() {

        return coreCaseDataApi.startCase(
            getAuthorisationHeader(),
            authTokenGenerator.generate(),
            CASE_TYPE_ID,
            CREATE_NEW_CASE
        );
    }

    @Override
    public SearchResult searchCase(String searchCriteria) {

        return coreCaseDataApi.searchCases(
            getAuthorisationHeader(),
            authTokenGenerator.generate(),
            CASE_TYPE_ID,
            searchCriteria
        );
    }

    private String getAuthorisationHeader() {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private String getIdamIdFormCcdToken(final String token) {
        String[] splitToken = token.split("\\.");
        Jwt parsedToken = Jwts.parser().parse(splitToken[0] + "." + splitToken[1] + ".");
        DefaultClaims body = (DefaultClaims)((DefaultJwt)parsedToken).getBody();
        return body.getSubject();
    }
}
