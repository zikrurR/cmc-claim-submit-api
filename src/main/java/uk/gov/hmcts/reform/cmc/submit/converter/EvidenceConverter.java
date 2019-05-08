package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class EvidenceConverter {


    public List<Evidence> from(List<CcdCollectionElement<CcdEvidenceRow>> ccdEvidence) {
        if (ccdEvidence == null) {
            return null;
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }

    public Evidence from(CcdCollectionElement<CcdEvidenceRow> collectionElement) {
        CcdEvidenceRow evidenceRow = collectionElement.getValue();
        if (evidenceRow == null || evidenceRow.getType() == null) {
            return null;
        }

        Evidence evidence = new Evidence();
        evidence.setId(collectionElement.getId());
        evidence.setType(EvidenceType.valueOf(evidenceRow.getType().name()));
        evidence.setDescription(evidenceRow.getDescription());

        return evidence;
    }
}
