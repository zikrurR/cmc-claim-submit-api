package uk.gov.hmcts.reform.cmc.submit.domain.models.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = ReferencePayment.class, name = "referencePayment"),
        @JsonSubTypes.Type(value = AccountPayment.class, name = "accountPayment")
    }
)

public interface Payment {

}
