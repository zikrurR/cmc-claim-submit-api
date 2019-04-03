package uk.gov.hmcts.reform.cmc.submit.domain.models.particulars;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DamagesExpectation {
    @JsonProperty("moreThanThousandPounds")
    MORE_THAN_THOUSAND_POUNDS,
    @JsonProperty("thousandPoundsOrLess")
    THOUSAND_POUNDS_OR_LESS;

}
