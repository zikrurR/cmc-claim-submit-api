package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ClaimAssert extends AbstractAssert<ClaimAssert, ClaimInput> {

    public ClaimAssert(ClaimInput actual) {
        super(actual, ClaimAssert.class);
    }

    public ClaimAssert isSameAs(Claim claim) {

        isNotNull();

        assertThat(actual.getAmount()).isEqualToComparingFieldByFieldRecursively(claim.getAmount());

        assertThat(actual.getEvidences().size()).isEqualTo(claim.getEvidences().size());

        for (int i = 0; i < actual.getEvidences().size(); i++) {
            assertThat(actual.getEvidences().get(i)).isEqualToComparingFieldByFieldRecursively(claim.getEvidences().get(i));
        }

        assertThat(actual.getClaimants().size()).isEqualTo(claim.getClaimants().size());

        for (int i = 0; i < actual.getClaimants().size(); i++) {
            assertThat(actual.getClaimants().get(i)).isEqualToComparingFieldByFieldRecursively(claim.getClaimants().get(i));
        }


        if (!Objects.equals(actual.getReason(), claim.getReason())) {
            failWithMessage("Expected claim.reason to be <%s> but was <%s>",
                    claim.getReason(), actual.getReason());
        }


        if (!Objects.equals(actual.getExternalId(), claim.getExternalId())) {
            failWithMessage("Expected claim.externalId to be <%s> but was <%s>",
                    claim.getExternalId(), actual.getExternalId().toString());
        }

        if (!Objects.equals(actual.getPreferredCourt(), claim.getPreferredCourt())) {
            failWithMessage("Expected claim.preferredCourt to be <%s> but was <%s>",
                    claim.getPreferredCourt(), actual.getPreferredCourt());
        }

        if (!actual.getAmount().getClass().equals(claim.getAmount().getClass())) {
            failWithMessage("Expected claim.amount to be <%s> but was <%s>",
                    claim.getAmount().getClass(), actual.getAmount().getClass());
        }

        return this;
    }

}
