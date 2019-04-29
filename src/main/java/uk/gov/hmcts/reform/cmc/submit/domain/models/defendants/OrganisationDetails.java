package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganisationDetails extends TheirDetails {

    private String contactPerson;

    private String companiesHouseNumber;

    @NotBlank
    private String name;

}
