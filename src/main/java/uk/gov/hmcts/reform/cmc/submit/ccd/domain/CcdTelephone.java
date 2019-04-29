package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

@Data
public class CcdTelephone {
    private String telephoneNumber;
    private String telephoneUsageType;
    private String contactDirection;
}
