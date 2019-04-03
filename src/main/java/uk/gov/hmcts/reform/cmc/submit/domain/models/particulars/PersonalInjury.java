package uk.gov.hmcts.reform.cmc.submit.domain.models.particulars;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalInjury {

    @NotNull
    private DamagesExpectation generalDamages;

}
