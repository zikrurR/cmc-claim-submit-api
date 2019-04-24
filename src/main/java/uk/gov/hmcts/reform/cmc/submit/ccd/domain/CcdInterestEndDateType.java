package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

public enum CcdInterestEndDateType {
    SETTLED_OR_JUDGMENT("settled_or_judgment"),
    SUBMISSION("submission");

    private String value;

    CcdInterestEndDateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
