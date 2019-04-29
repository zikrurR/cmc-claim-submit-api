package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyDetails extends TheirDetails {

    private String contactPerson;

    @NotBlank
    private String name;

}
