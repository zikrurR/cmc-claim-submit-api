package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IndividualDetails extends TheirDetails {

    private LocalDate dateOfBirth;

    private String title;
    
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

}
