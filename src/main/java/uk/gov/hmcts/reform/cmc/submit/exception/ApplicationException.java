package uk.gov.hmcts.reform.cmc.submit.exception;

import java.util.Objects;

public class ApplicationException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum ApplicationErrorCode {
        CASE_ID_DOES_NOT_EXIST,
        MORE_THAN_ONE_CASES_FOR_THE_REFERENCE
    }

    private final ApplicationErrorCode applicationErrorCode;

    public ApplicationException(final ApplicationErrorCode applicationErrorCode) {
        super(Objects.toString(applicationErrorCode));
        this.applicationErrorCode = applicationErrorCode;
    }

    public ApplicationErrorCode getApplicationErrorCode() {
        return applicationErrorCode;
    }

}
