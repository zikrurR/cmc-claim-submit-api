package uk.gov.hmcts.reform.cmc.submit.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.EventRequestData;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.ClaimMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;
import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException;
import uk.gov.hmcts.reform.cmc.submit.exception.CoreCaseDataStoreException;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;

import java.util.Map;

@Service("claimService")
public class ClaimServiceImpl implements ClaimService {


    public static final String JURISDICTION_ID = "CMC";
    public static final String CREATE_NEW_CASE = "IssueClaim";
    public static final String CASE_TYPE_ID = "MoneyClaimCase";
    public static final String CMC_CASE_CREATE_SUMMARY = "CMC case issue";
    public static final String SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION = "Submitting CMC case issue";
    public static final String CCD_STORING_FAILURE_MESSAGE = "Failed storing claim in CCD store for case id %s on event %s";

    private final ClaimMapper caseMapper;
    private final CoreCaseDataApi coreCaseDataApi;
    private final AuthTokenGenerator authTokenGenerator;
    private final ObjectMapper objectMapper;

    public ClaimServiceImpl(
            ClaimMapper caseMapper,
            CoreCaseDataApi coreCaseDataApi,
            AuthTokenGenerator authTokenGenerator,
            ObjectMapper objectMapper) {

        this.caseMapper = caseMapper;
        this.coreCaseDataApi = coreCaseDataApi;
        this.authTokenGenerator = authTokenGenerator;
        this.objectMapper = objectMapper;
    }

    @Override
    public Claim getClaim(String externalIdentifier, String authorisation) throws ApplicationException  {
        Claim claim;
        try {
            claim = getClaimByReference(externalIdentifier, authorisation);
        } catch (CoreCaseDataStoreException e) {
            claim = getClaimByExternalId(externalIdentifier, authorisation);
        }

        return claim;
    }

    private Claim getClaimByReference(String reference, String authorisation) throws CoreCaseDataStoreException {
        // @TODO
        return null;
    }

    private Claim getClaimByExternalId(String externalId, String authorisation) throws CoreCaseDataStoreException {
        // @TODO
        return null;
    }


    @Override
    public ClaimOutput createNewCase(ClaimInput claimData, String authorisation) {

        String idamId = ""; // should be not needed as far as we can get it from the start event token
        CcdCase ccdCase = caseMapper.to(claimData);

        StartEventResponse startEventResponse;
        try {
            EventRequestData eventRequestData = EventRequestData.builder()
                .userId(idamId)
                .jurisdictionId(JURISDICTION_ID)
                .caseTypeId(CASE_TYPE_ID)
                .eventId(CREATE_NEW_CASE)
                .ignoreWarning(true)
                .build();

            startEventResponse = startCreate(authorisation, eventRequestData);

        } catch (Exception exception) {
            throw new CoreCaseDataStoreException(
                String.format(
                    CCD_STORING_FAILURE_MESSAGE,
                    ccdCase.getExternalId(),
                    CREATE_NEW_CASE
                ), exception
            );
        }

        CaseDetails caseDetails;
        try {

            EventRequestData eventRequestData = EventRequestData.builder()
                    .userId(idamId)
                    .jurisdictionId(JURISDICTION_ID)
                    .caseTypeId(CASE_TYPE_ID)
                    .eventId(CREATE_NEW_CASE)
                    .ignoreWarning(true)
                    .build();

            CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder()
                    .id(startEventResponse.getEventId())
                    .summary(CMC_CASE_CREATE_SUMMARY)
                    .description(SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION)
                    .build())
                .data(ccdCase)
                .build();

            caseDetails = submitCreate(
                authorisation,
                eventRequestData,
                caseDataContent
            );

        } catch (Exception exception) {
            throw new CoreCaseDataStoreException(
                String.format(
                    CCD_STORING_FAILURE_MESSAGE,
                    ccdCase.getExternalId(),
                    CREATE_NEW_CASE
                ), exception
            );
        }

        return extractClaimOutput(caseDetails);
    }

    private CaseDetails submitCreate(String authorisation, EventRequestData eventRequestData, CaseDataContent caseDataContent) {

        return coreCaseDataApi.submitForCitizen(
            authorisation,
            this.authTokenGenerator.generate(),
            eventRequestData.getUserId(),
            eventRequestData.getJurisdictionId(),
            eventRequestData.getCaseTypeId(),
            eventRequestData.isIgnoreWarning(),
            caseDataContent
        );
    }

    private StartEventResponse startCreate(String authorisation, EventRequestData eventRequestData) {

        return coreCaseDataApi.startForCitizen(
            authorisation,
            authTokenGenerator.generate(),
            eventRequestData.getUserId(),
            eventRequestData.getJurisdictionId(),
            eventRequestData.getCaseTypeId(),
            eventRequestData.getEventId()
        );
    }

    private ClaimOutput extractClaimOutput(CaseDetails caseDetails) {

        ClaimOutput claimOutput = new ClaimOutput();
        claimOutput.setReferenceNumber((String)caseDetails.getData().get("referenceNumber"));
        return claimOutput;
    }

    private CcdCase extractCase(CaseDetails caseDetails) {
        Map<String, Object> caseData = caseDetails.getData();
        caseData.put("id", caseDetails.getId());
        return objectMapper.convertValue(caseData, CcdCase.class);
    }

}
