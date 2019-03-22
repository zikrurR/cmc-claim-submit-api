package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.Test;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CCDTimelineEvent;
import uk.gov.hmcts.cmc.domain.models.timeline.TimelineEvent;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.timeline.TimelineMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class TimelineMapperTest {

    private TimelineMapper mapper = new TimelineMapper();

    @Test
    public void shouldMapTimelineEventToCCD() {
        //given
        TimelineEvent timelineEvent = SampleTimelineEvent.validDefaults();

        //when
        List<CCDCollectionElement<CCDTimelineEvent>> ccdTimelineEvent = mapper.to(Arrays.asList(timelineEvent));

        //then
        assertThat(ccdTimelineEvent.size()).isEqualTo(1);
        assertThat(ccdTimelineEvent.get(0).getValue().getDate()).isEqualTo(timelineEvent.getDate());
        assertThat(ccdTimelineEvent.get(0).getValue().getDescription()).isEqualTo(timelineEvent.getDescription());
        assertThat(ccdTimelineEvent.get(0).getId()).isEqualTo(timelineEvent.getId());
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
        List<TimelineEvent> timelineEvent = mapper.from(Arrays.asList(CCDCollectionElement.<CCDTimelineEvent>builder()
            .id(collectionId)
            .value(ccdTimelineEvent)
            .build())
        );

        //then
        assertThat(timelineEvent.size()).isEqualTo(1);
        assertThat(timelineEvent.get(0)).isEqualTo(ccdTimelineEvent);
        assertThat(timelineEvent.get(0).getId()).isEqualTo(collectionId);
    }
}
