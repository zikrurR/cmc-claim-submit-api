package uk.gov.hmcts.reform.cmc.submit.services;

import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.Map;

public interface CoreCaseDataService {

    StartEventResponse startCase();

    SearchResult searchCase(String elasticSearchCriteria);

    CaseDetails submitCase(StartEventResponse startEventResponse, Map<String, Object> ccdCaseData);

}
