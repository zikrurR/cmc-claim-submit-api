package uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;

import java.util.HashMap;
import java.util.Map;

public class CcdTimelineEventBuilder implements Builder<CcdTimelineEvent> {
    private String date;
    private String description;

    private Map<String, Object> propertiesMap = new HashMap<>();

    public static CcdTimelineEventBuilder builder() {
        return new CcdTimelineEventBuilder();
    }

    private CcdTimelineEventBuilder() {
    }

    @Override
    public CcdTimelineEvent build() {
        CcdTimelineEvent ccdTimelineEvent = new CcdTimelineEvent();
        ccdTimelineEvent.setDate(date);
        ccdTimelineEvent.setDescription(description);
        return ccdTimelineEvent;
    }

    public Map<String, Object> buildMap() {
        return propertiesMap;
    }

    public CcdTimelineEventBuilder date(String date) {
        this.date = date;
        propertiesMap.put("date", date);
        return this;
    }

    public CcdTimelineEventBuilder description(String description) {
        this.description = description;
        propertiesMap.put("description", description);
        return this;
    }

}