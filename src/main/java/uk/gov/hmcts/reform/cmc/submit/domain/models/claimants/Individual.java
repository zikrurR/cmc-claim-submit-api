package uk.gov.hmcts.reform.cmc.submit.domain.models.claimants;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class Individual extends Party {

    @JsonUnwrapped
    private LocalDate dateOfBirth;

}
