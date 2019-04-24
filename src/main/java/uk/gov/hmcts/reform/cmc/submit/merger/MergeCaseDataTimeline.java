package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class MergeCaseDataTimeline implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {

        ccdCase.setTimeline(to(claim.getTimeline()));

    }

    private List<CcdCollectionElement<CcdTimelineEvent>> to(List<TimelineEvent> timeline) {
        if (timeline == null) {
            return new ArrayList<>();
        }

        return timeline.stream()
                .map(this::to)
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


}
