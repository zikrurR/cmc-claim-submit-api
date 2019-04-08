package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class TimelineConverter {

    public List<TimelineEvent> from(List<CcdCollectionElement<CcdTimelineEvent>> ccdTimeline) {
        if (ccdTimeline == null) {
            return new ArrayList<>();
        }

        return ccdTimeline.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    private TimelineEvent from(CcdCollectionElement<CcdTimelineEvent> ccdTimelineEvent) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setId(ccdTimelineEvent.getId());
        timelineEvent.setDescription(ccdTimelineEvent.getValue().getDescription());
        timelineEvent.setDate(ccdTimelineEvent.getValue().getDate());

        return timelineEvent;
    }

}
