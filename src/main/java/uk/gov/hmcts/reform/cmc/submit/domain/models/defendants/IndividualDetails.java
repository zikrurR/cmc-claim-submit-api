package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndividualDetails extends TheirDetails {

    private LocalDate dateOfBirth;

}
