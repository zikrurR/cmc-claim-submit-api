package uk.gov.hmcts.reform.cmc.submit.domain.models.claimants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SoleTrader extends Party {

    private String title;

    private String businessName;

}
