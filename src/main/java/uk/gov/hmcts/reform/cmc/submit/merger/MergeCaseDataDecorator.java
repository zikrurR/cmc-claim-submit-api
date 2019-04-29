package uk.gov.hmcts.reform.cmc.submit.merger;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

public interface MergeCaseDataDecorator {

    void merge(CcdCaseBuilder ccdCase, ClaimInput claim);

}
