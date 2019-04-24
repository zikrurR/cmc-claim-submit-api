package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CcdTelephone {
    private String telephoneNumber;
    private String telephoneUsageType;
    private String contactDirection;
}
