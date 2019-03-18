package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.Test;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CCDTimelineEvent;
import uk.gov.hmcts.cmc.domain.models.timeline.TimelineEvent;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class TimelineEventMapperTest {

    private TimelineEventMapper mapper = new TimelineEventMapper();

    @Test
    public void shouldMapTimelineEventToCCD() {
        //given
        TimelineEvent timelineEvent = TimelineEvent.builder().eventDate("Last Year").description("Work done").build();

        //when
        CCDCollectionElement<CCDTimelineEvent> ccdTimelineEvent = mapper.to(timelineEvent);

        //then
        assertThat(timelineEvent).isEqualTo(ccdTimelineEvent.getValue());
        assertThat(timelineEvent.getId()).isEqualTo(ccdTimelineEvent.getId());
    }

    @Test
    public void shouldTimelineEventFromCCD() {
        //given
        CCDTimelineEvent ccdTimelineEvent = CCDTimelineEvent.builder()
            .date("Last Month")
            .description("My description")
            .build();

        String collectionId = UUID.randomUUID().toString();

        //when
        TimelineEvent timelineEvent = mapper.from(CCDCollectionElement.<CCDTimelineEvent>builder()
            .id(collectionId)
            .value(ccdTimelineEvent)
            .build()
        );

        //then
        assertThat(timelineEvent).isEqualTo(ccdTimelineEvent);
        assertThat(timelineEvent.getId()).isEqualTo(collectionId);
    }
}
