package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.timeline;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CCDTimelineEvent;
import uk.gov.hmcts.cmc.domain.models.timeline.TimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimelineMapper implements Mapper<List<CCDCollectionElement<CCDTimelineEvent>>, List<TimelineEvent>> {

    @Override
    public List<CCDCollectionElement<CCDTimelineEvent>> to(List<TimelineEvent> timeline) {
        if (timeline == null) {
            return new ArrayList<>();
        }

        return timeline.stream()
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimelineEvent> from(List<CCDCollectionElement<CCDTimelineEvent>> ccdTimeline) {
        if (ccdTimeline == null) {
            return new ArrayList<>();
        }

        return ccdTimeline.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }


    private CCDCollectionElement<CCDTimelineEvent> to(TimelineEvent timelineEvent) {
        if (timelineEvent == null) {
            return null;
        }

        CCDTimelineEvent ccdTimelineEvent = CCDTimelineEvent.builder()
                                                    .date(timelineEvent.getDate())
                                                    .description(timelineEvent.getDescription())
                                                    .build();

        return CCDCollectionElement.<CCDTimelineEvent>builder()
            .value(ccdTimelineEvent)
            .id(timelineEvent.getId())
            .build();
    }


    private TimelineEvent from(CCDCollectionElement<CCDTimelineEvent> ccdTimelineEvent) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setId(ccdTimelineEvent.getId());
        timelineEvent.setDescription(ccdTimelineEvent.getValue().getDescription());
        timelineEvent.setDate(ccdTimelineEvent.getValue().getDate());

        return timelineEvent;
    }
}
