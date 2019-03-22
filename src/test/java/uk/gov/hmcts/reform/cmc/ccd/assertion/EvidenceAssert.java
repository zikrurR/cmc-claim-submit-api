package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.evidence.CCDEvidenceRow;
import uk.gov.hmcts.cmc.domain.models.evidence.Evidence;

import java.util.Objects;

public class EvidenceAssert extends AbstractAssert<EvidenceAssert, Evidence> {

    public EvidenceAssert(Evidence actual) {
        super(actual, EvidenceAssert.class);
    }

    public EvidenceAssert isEqualTo(CCDEvidenceRow ccdEvidenceRow) {
        isNotNull();

        if (!Objects.equals(actual.getType().name(), ccdEvidenceRow.getType().name())) {
            failWithMessage("Expected EvidenceRow.type to be <%s> but was <%s>",
                ccdEvidenceRow.getType().name(), actual.getType().name());
        }

        if (!Objects.equals(actual.getDescription(), ccdEvidenceRow.getDescription())) {
            failWithMessage("Expected EvidenceRow.description to be <%s> but was <%s>",
                ccdEvidenceRow.getDescription(), actual.getDescription());
        }

        return this;
    }

}
