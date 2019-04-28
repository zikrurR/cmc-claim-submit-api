package uk.gov.hmcts.reform.cmc.ccd.assertion;

import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

public class Assertions {

    private Assertions() {
    }

    public static ClaimAssert assertThat(ClaimInput claim) {
        return new ClaimAssert(claim);
    }
}
