package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcdTimelineEvent {

    private String date;
    private String description;

}
