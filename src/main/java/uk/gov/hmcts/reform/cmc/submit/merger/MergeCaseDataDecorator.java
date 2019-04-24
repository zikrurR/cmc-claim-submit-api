package uk.gov.hmcts.reform.cmc.submit.merger;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

public interface MergeCaseDataDecorator {

    void merge(CcdCase ccdCase, ClaimInput claim);

}
