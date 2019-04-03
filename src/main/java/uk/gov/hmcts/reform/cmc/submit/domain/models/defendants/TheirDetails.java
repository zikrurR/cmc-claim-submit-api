package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class and its subtypes represent the data that a person provides about the other party.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = IndividualDetails.class, name = "individual"),
        @JsonSubTypes.Type(value = SoleTraderDetails.class, name = "soleTrader"),
        @JsonSubTypes.Type(value = CompanyDetails.class, name = "company"),
        @JsonSubTypes.Type(value = OrganisationDetails.class, name = "organisation")
    }
)
@Data
public abstract class TheirDetails {

    private String id;

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private Address address;

    @Email(regexp = "\\S+")
    private String email;

    @Valid
    private Representative representative;

    @Valid
    private Address serviceAddress;


}
