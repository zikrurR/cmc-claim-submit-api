package uk.gov.hmcts.reform.cmc.submit.services;


import uk.gov.hmcts.cmc.domain.models.ClaimData;

public interface ClaimService {

    ClaimData getClaimByExternalId(String externalId, String authorisation);

    ClaimData getClaimByReference(String reference, String authorisation);

    ClaimData createNewCase(ClaimData claimData, String authorisation);

}
