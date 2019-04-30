package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CcdEvidenceRow {
    @NotNull
    private CcdEvidenceType type;

    private String description;
}
