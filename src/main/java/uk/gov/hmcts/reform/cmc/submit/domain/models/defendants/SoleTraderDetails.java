package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class SoleTraderDetails extends TheirDetails {

    private String title;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String businessName;
}
