package uk.gov.hmcts.reform.cmc.submit.services;

import com.fasterxml.jackson.databind.JsonNode;

import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.List;
import java.util.Map;

public interface CoreCaseDataService {

    StartEventResponse startCase();

    List<CaseDetails> searchCase(Map<String, String> searchCriteria);

    CaseDetails submitCase(StartEventResponse startEventResponse, Map<String, JsonNode> ccdCaseData);

}
