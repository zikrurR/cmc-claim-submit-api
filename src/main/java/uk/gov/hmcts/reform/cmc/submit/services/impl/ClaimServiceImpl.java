package uk.gov.hmcts.reform.cmc.submit.services.impl;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.EventRequestData;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.cmc.ccd.mapper.ClaimMapper;
import uk.gov.hmcts.reform.cmc.submit.exception.CoreCaseDataStoreException;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static uk.gov.hmcts.cmc.ccd.domain.CaseEvent.CREATE_NEW_CASE;

@Service
public class ClaimServiceImpl implements ClaimService {


    public static final String JURISDICTION_ID = "CMC";
    public static final String CASE_TYPE_ID = "MoneyClaimCase";
    public static final String CMC_CASE_CREATE_SUMMARY = "CMC case issue";
    public static final String SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION = "Submitting CMC case issue";
    public static final String CCD_STORING_FAILURE_MESSAGE = "Failed storing claim in CCD store for case id %s on event %s";

    private ClaimMapper caseMapper;
   // private final UserService userService;
    private CoreCaseDataApi coreCaseDataApi;
    private AuthTokenGenerator authTokenGenerator;
    private ObjectMapper objectMapper;

    public ClaimData getClaimByExternalId(String externalId, String authorisation) {
        return null;
    }

    public ClaimData getClaimByReference(String reference, String authorisation) {
        return null;
    }

    public ClaimData createNewCase(ClaimData claimData, String authorisation) {

        boolean isRepresented = true;
        String idamId = "";//userService.getUserDetails(authorisation);
        CCDCase ccdCase = caseMapper.to(claimData);

        try {
            EventRequestData eventRequestData = EventRequestData.builder()
                .userId(idamId)
                .jurisdictionId(JURISDICTION_ID)
                .caseTypeId(CASE_TYPE_ID)
                .eventId(CREATE_NEW_CASE.getValue())
                .ignoreWarning(true)
                .build();

            StartEventResponse startEventResponse = startCreate(authorisation, eventRequestData, isRepresented);

            CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder()
                    .id(startEventResponse.getEventId())
                    .summary(CMC_CASE_CREATE_SUMMARY)
                    .description(SUBMITTING_CMC_CASE_ISSUE_DESCRIPTION)
                    .build())
                .data(ccdCase)
                .build();

            CaseDetails caseDetails = submitCreate(
                authorisation,
                eventRequestData,
                caseDataContent,
                isRepresented
            );

            return extractClaim(caseDetails);

        } catch (Exception exception) {
            throw new CoreCaseDataStoreException(
                String.format(
                    CCD_STORING_FAILURE_MESSAGE,
                    ccdCase.getReferenceNumber(),
                    CREATE_NEW_CASE
                ), exception
            );
        }

    }

    private CaseDetails submitCreate(
        String authorisation,
        EventRequestData eventRequestData,
        CaseDataContent caseDataContent,
        boolean represented
    ) {
        if (represented) {
            return coreCaseDataApi.submitForCaseworker(
                authorisation,
                this.authTokenGenerator.generate(),
                eventRequestData.getUserId(),
                eventRequestData.getJurisdictionId(),
                eventRequestData.getCaseTypeId(),
                eventRequestData.isIgnoreWarning(),
                caseDataContent
            );
        }

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

    private StartEventResponse startCreate(String authorisation, EventRequestData eventRequestData, boolean isRepresented) {
        if (isRepresented) {
            return coreCaseDataApi.startForCaseworker(
                authorisation,
                authTokenGenerator.generate(),
                eventRequestData.getUserId(),
                eventRequestData.getJurisdictionId(),
                eventRequestData.getCaseTypeId(),
                eventRequestData.getEventId()
            );
        }

        return coreCaseDataApi.startForCitizen(
            authorisation,
            authTokenGenerator.generate(),
            eventRequestData.getUserId(),
            eventRequestData.getJurisdictionId(),
            eventRequestData.getCaseTypeId(),
            eventRequestData.getEventId()
        );
    }

    private ClaimData extractClaim(CaseDetails caseDetails) {
        return caseMapper.from(extractCase(caseDetails));
    }

    private CCDCase extractCase(CaseDetails caseDetails) {
        Map<String, Object> caseData = caseDetails.getData();
        caseData.put("id", caseDetails.getId());
        return objectMapper.convertValue(caseData, CCDCase.class);
    }

}
