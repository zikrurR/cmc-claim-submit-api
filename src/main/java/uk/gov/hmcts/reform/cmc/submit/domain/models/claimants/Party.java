package uk.gov.hmcts.reform.cmc.submit.domain.models.claimants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class and its subtypes represent the data that a person provides about themselves.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = Individual.class, name = "individual"),
        @JsonSubTypes.Type(value = SoleTrader.class, name = "soleTrader"),
        @JsonSubTypes.Type(value = Company.class, name = "company"),
        @JsonSubTypes.Type(value = Organisation.class, name = "organisation")
    }
)
@Data
public abstract class Party {

    @NotBlank
    @Size(max = 255, message = "may not be longer than {max} characters")
    private String name;

    private String id;

    @Valid
    @NotNull
    private Address address;

    @Valid
    private Address correspondenceAddress;

    @Size(max = 30, message = "may not be longer than {max} characters")
    private String mobilePhone;

    @Valid
    private Representative representative;

}
