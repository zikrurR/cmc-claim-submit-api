package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdTimelineEventBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class MergeCaseDataTimeline implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCaseBuilder ccdCase, ClaimInput claim) {

        ccdCase.timeline(to(claim.getTimeline()));

    }

    private List<CcdCollectionElementBuilder<CcdTimelineEvent>> to(List<TimelineEvent> timeline) {
        if (timeline == null) {
            return new ArrayList<>();
        }

        return timeline.stream()
                .map(this::to)
                .collect(Collectors.toList());
    }

    private CcdCollectionElementBuilder<CcdTimelineEvent> to(TimelineEvent timelineEvent) {
        if (timelineEvent == null) {
            return null;
        }

        CcdTimelineEventBuilder ccdTimelineEvent = CcdTimelineEventBuilder.builder()
                                                    .date(timelineEvent.getDate())
                                                    .description(timelineEvent.getDescription());

        return CcdCollectionElementBuilder.<CcdTimelineEvent>builder()
            .value(ccdTimelineEvent)
            .id(timelineEvent.getId());
    }


}
