package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdEvidenceRowBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class MergeCaseDataEvidence implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        ccdCaseBuilder.evidence(to(claim.getEvidences()));
    }

    public List<CcdCollectionElementBuilder<CcdEvidenceRow>> to(List<Evidence> evidences) {
        if (evidences == null) {
            return new ArrayList<>();
        }


        return evidences.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    public CcdCollectionElementBuilder<CcdEvidenceRow> to(Evidence evidence) {
        if (evidence == null) {
            return null;
        }

        CcdEvidenceRowBuilder ccdEvidenceRow = CcdEvidenceRowBuilder.builder()
                           .type(CcdEvidenceType.valueOf(evidence.getType().name()))
                           .description(evidence.getDescription());

        return CcdCollectionElementBuilder.<CcdEvidenceRow>builder()
                .value(ccdEvidenceRow)
                .id(evidence.getId());
    }

}
