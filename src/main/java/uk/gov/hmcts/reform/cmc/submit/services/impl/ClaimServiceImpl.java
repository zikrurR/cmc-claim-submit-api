package uk.gov.hmcts.reform.cmc.submit.services.impl;

import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;

import org.springframework.stereotype.Service;

@Service
public class ClaimServiceImpl implements ClaimService {

    public ClaimData getClaimByExternalId(String externalId, String authorisation) {
        return null;
    }

    public ClaimData getClaimByReference(String reference, String authorisation) {
        return null;
    }

    public ClaimData saveClaim(ClaimData claimData, String authorisation) {
        return null;
    }

}
