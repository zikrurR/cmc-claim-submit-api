package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

@Data
public class CcdEvidenceRow {
    private CcdEvidenceType type;
    private String description;
}
