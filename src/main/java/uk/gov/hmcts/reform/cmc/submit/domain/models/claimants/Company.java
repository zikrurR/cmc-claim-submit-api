package uk.gov.hmcts.reform.cmc.submit.domain.models.claimants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends Party {

    private String contactPerson;

}
