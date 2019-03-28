package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.evidence;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceRow;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceType;
import uk.gov.hmcts.cmc.domain.models.evidence.Evidence;
import uk.gov.hmcts.cmc.domain.models.evidence.EvidenceType;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EvidenceMapper implements Mapper<List<CcdCollectionElement<CcdEvidenceRow>>, List<Evidence>> {

    @Override
    public List<CcdCollectionElement<CcdEvidenceRow>> to(List<Evidence> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Evidence> from(List<CcdCollectionElement<CcdEvidenceRow>> ccdEvidence) {
        if (ccdEvidence == null) {
            return new ArrayList<>();
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }


    public CcdCollectionElement<CcdEvidenceRow> to(Evidence evidence) {
        if (evidence == null) {
            return null;
        }

        CcdEvidenceRow ccdEvidenceRow = CcdEvidenceRow.builder()
                           .type(CcdEvidenceType.valueOf(evidence.getType().name()))
                           .description(evidence.getDescription())
                           .build();

        return CcdCollectionElement.<CcdEvidenceRow>builder()
                .value(ccdEvidenceRow)
                .id(evidence.getId())
                .build();
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
