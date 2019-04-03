package uk.gov.hmcts.reform.cmc.submit.domain.models.evidence;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Evidence {

    private String id;

    @NotNull
    private EvidenceType type;

    @Size(max = 99000)
    private String description;

}
