package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

@Data
public class CcdTimelineEvent {

    private String date;
    private String description;

}
