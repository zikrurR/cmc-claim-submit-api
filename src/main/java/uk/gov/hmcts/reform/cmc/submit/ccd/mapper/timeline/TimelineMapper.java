package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.timeline;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.Mapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimelineMapper implements Mapper<List<CcdCollectionElement<CcdTimelineEvent>>, List<TimelineEvent>> {

    @Override
    public List<CcdCollectionElement<CcdTimelineEvent>> to(List<TimelineEvent> timeline) {
        if (timeline == null) {
            return new ArrayList<>();
        }

        return timeline.stream()
                .map(this::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimelineEvent> from(List<CcdCollectionElement<CcdTimelineEvent>> ccdTimeline) {
        if (ccdTimeline == null) {
            return new ArrayList<>();
        }

        return ccdTimeline.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }


    private CcdCollectionElement<CcdTimelineEvent> to(TimelineEvent timelineEvent) {
        if (timelineEvent == null) {
            return null;
        }

        CcdTimelineEvent ccdTimelineEvent = CcdTimelineEvent.builder()
                                                    .date(timelineEvent.getDate())
                                                    .description(timelineEvent.getDescription())
                                                    .build();

        return CcdCollectionElement.<CcdTimelineEvent>builder()
            .value(ccdTimelineEvent)
            .id(timelineEvent.getId())
            .build();
    }


    private TimelineEvent from(CcdCollectionElement<CcdTimelineEvent> ccdTimelineEvent) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setId(ccdTimelineEvent.getId());
        timelineEvent.setDescription(ccdTimelineEvent.getValue().getDescription());
        timelineEvent.setDate(ccdTimelineEvent.getValue().getDate());

        return timelineEvent;
    }
}
