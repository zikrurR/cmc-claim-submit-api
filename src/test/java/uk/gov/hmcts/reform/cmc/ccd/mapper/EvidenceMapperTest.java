package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceRow;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CcdEvidenceType;
import uk.gov.hmcts.cmc.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleEvidence;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.evidence.EvidenceMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class EvidenceMapperTest {

    private EvidenceMapper mapper = new EvidenceMapper();

    @Test
    public void shouldMapEvidenceToCcd() {
        //given
        Evidence evidence = SampleEvidence.validDefaults();

        //when
        CcdCollectionElement<CcdEvidenceRow> ccdEvidence = mapper.to(evidence);

        //then
        assertThat(evidence).isEqualTo(ccdEvidence.getValue());
        assertThat(evidence.getId()).isEqualTo(ccdEvidence.getId());
    }

    @Test
    public void shouldMapEvidenceToCcdWhenNoDescriptionProvided() {
        //given
        Evidence evidence = SampleEvidence.validDefaults();
        evidence.setDescription(null);
        //when
        CcdCollectionElement<CcdEvidenceRow> ccdEvidence = mapper.to(evidence);

        //then
        assertThat(evidence).isEqualTo(ccdEvidence.getValue());
        assertThat(evidence.getId()).isEqualTo(ccdEvidence.getId());
    }

    @Test
    public void shouldMapEvidenceFromCcd() {
        //given
        CcdEvidenceRow ccdEvidence = CcdEvidenceRow.builder()
            .description("My description")
            .type(CcdEvidenceType.EXPERT_WITNESS)
            .build();

        //when
        Evidence evidence = mapper.from(CcdCollectionElement.<CcdEvidenceRow>builder()
            .value(ccdEvidence).build());

        //then
        assertThat(evidence).isEqualTo(ccdEvidence);
    }

    @Test
    public void shouldMapEvidenceFromCcdWhenNoDescriptionProvided() {
        //given
        CcdEvidenceRow ccdEvidence = CcdEvidenceRow.builder()
            .type(CcdEvidenceType.EXPERT_WITNESS)
            .build();

        //when
        Evidence evidence = mapper.from(CcdCollectionElement.<CcdEvidenceRow>builder()
            .value(ccdEvidence).build());

        //then
        assertThat(evidence).isEqualTo(ccdEvidence);
    }
}
