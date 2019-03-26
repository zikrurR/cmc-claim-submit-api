package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.evidence;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CCDEvidenceRow;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CCDEvidenceType;
import uk.gov.hmcts.cmc.domain.models.evidence.Evidence;
import uk.gov.hmcts.cmc.domain.models.evidence.EvidenceType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EvidenceMapper implements Mapper<List<CCDCollectionElement<CCDEvidenceRow>>, List<Evidence>> {

    @Override
    public List<CCDCollectionElement<CCDEvidenceRow>> to(List<Evidence> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Evidence> from(List<CCDCollectionElement<CCDEvidenceRow>> ccdEvidence) {
        if (ccdEvidence == null) {
            return new ArrayList<>();
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }


    public CCDCollectionElement<CCDEvidenceRow> to(Evidence evidence) {
        if (evidence == null) {
            return null;
        }

        CCDEvidenceRow ccdEvidenceRow = CCDEvidenceRow.builder()
                           .type(CCDEvidenceType.valueOf(evidence.getType().name()))
                           .description(evidence.getDescription())
                           .build();

        return CCDCollectionElement.<CCDEvidenceRow>builder()
                .value(ccdEvidenceRow)
                .id(evidence.getId())
                .build();
    }

    public Evidence from(CCDCollectionElement<CCDEvidenceRow> collectionElement) {
        CCDEvidenceRow evidenceRow = collectionElement.getValue();
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
