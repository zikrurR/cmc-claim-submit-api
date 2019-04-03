package uk.gov.hmcts.reform.cmc.submit.domain.models.common;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.constraints.Postcode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Address {

    @NotBlank(message = "Address Line1 should not be empty")
    private String line1;

    private String line2;

    private String line3;

    @NotBlank(message = "City/town should not be empty")
    private String city;

    @NotNull
    @Postcode
    private String postcode;

}
