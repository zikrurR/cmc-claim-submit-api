package uk.gov.hmcts.reform.cmc.submit.domain.models.timeline;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TimelineEvent {

    private String id;

    @NotBlank
    private String date;

    @NotBlank
    private String description;
}
