package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceRow;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceType;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class MergeCaseDataEvidence implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {
        ccdCase.setEvidence(to(claim.getEvidences()));
    }

    public List<CcdCollectionElement<CcdEvidenceRow>> to(List<Evidence> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
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

}
