package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.Test;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CCDEvidenceRow;
import uk.gov.hmcts.cmc.ccd.domain.evidence.CCDEvidenceType;
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
        CCDCollectionElement<CCDEvidenceRow> ccdEvidence = mapper.to(evidence);

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
        CCDCollectionElement<CCDEvidenceRow> ccdEvidence = mapper.to(evidence);

        //then
        assertThat(evidence).isEqualTo(ccdEvidence.getValue());
        assertThat(evidence.getId()).isEqualTo(ccdEvidence.getId());
    }

    @Test
    public void shouldMapEvidenceFromCcd() {
        //given
        CCDEvidenceRow ccdEvidence = CCDEvidenceRow.builder()
            .description("My description")
            .type(CCDEvidenceType.EXPERT_WITNESS)
            .build();

        //when
        Evidence evidence = mapper.from(CCDCollectionElement.<CCDEvidenceRow>builder()
            .value(ccdEvidence).build());

        //then
        assertThat(evidence).isEqualTo(ccdEvidence);
    }

    @Test
    public void shouldMapEvidenceFromCcdWhenNoDescriptionProvided() {
        //given
        CCDEvidenceRow ccdEvidence = CCDEvidenceRow.builder()
            .type(CCDEvidenceType.EXPERT_WITNESS)
            .build();

        //when
        Evidence evidence = mapper.from(CCDCollectionElement.<CCDEvidenceRow>builder()
            .value(ccdEvidence).build());

        //then
        assertThat(evidence).isEqualTo(ccdEvidence);
    }
}
