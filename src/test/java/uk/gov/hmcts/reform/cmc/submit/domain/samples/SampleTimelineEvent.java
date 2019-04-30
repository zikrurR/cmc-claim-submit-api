package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

public class SampleTimelineEvent {

    private SampleTimelineEvent() {
        super();
    }

    public static TimelineEvent validDefaults() {

        TimelineEvent timelineEvent = new TimelineEvent();

        timelineEvent.setDate("Last Year");
        timelineEvent.setDescription("signed a contract");
        timelineEvent.setId("359fda9d-e5fd-4d6e-9525-238642d0157d");

        return timelineEvent;
    }
}
