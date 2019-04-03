package uk.gov.hmcts.reform.cmc.submit.domain.models.common;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Representative {

    @NotBlank
    private String organisationName;

    @Valid
    @NotNull
    private Address organisationAddress;

    @Valid
    private ContactDetails organisationContactDetails;

}
