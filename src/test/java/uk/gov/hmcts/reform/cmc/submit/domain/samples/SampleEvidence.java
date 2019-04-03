package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;

import static uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType.CORRESPONDENCE;

public class SampleEvidence {


    private SampleEvidence() {
        super();
    }

    public static Evidence validDefaults() {
        Evidence evidenceRow = new Evidence();
        evidenceRow.setId("d839f2f0-025f-4ee9-9a98-16bbe6ab3b35");
        evidenceRow.setType(CORRESPONDENCE);
        evidenceRow.setDescription("description");

        return evidenceRow;
    }

}
