package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

public enum CcdPartyType {
    INDIVIDUAL("Individual"),
    ORGANISATION("Organisation"),
    SOLE_TRADER("SoleTrader"),
    COMPANY("Company");

    private String value;

    CcdPartyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
