package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.Objects;

public class TimelineEventAssert extends AbstractAssert<TimelineEventAssert, TimelineEvent> {

    public TimelineEventAssert(TimelineEvent actual) {
        super(actual, TimelineEventAssert.class);
    }

    public TimelineEventAssert isEqualTo(CcdTimelineEvent ccdTimelineEvent) {
        isNotNull();

        if (!Objects.equals(actual.getDate(), ccdTimelineEvent.getDate())) {
            failWithMessage("Expected TimelineEvent.date to be <%s> but was <%s>",
                ccdTimelineEvent.getDate(), actual.getDate());
        }

        if (!Objects.equals(actual.getDescription(), ccdTimelineEvent.getDescription())) {
            failWithMessage("Expected TimelineEvent.description to be <%s> but was <%s>",
                ccdTimelineEvent.getDescription(), actual.getDescription());
        }

        return this;
    }

}
