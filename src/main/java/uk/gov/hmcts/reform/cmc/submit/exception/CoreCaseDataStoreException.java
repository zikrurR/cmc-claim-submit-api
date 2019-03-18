package uk.gov.hmcts.reform.cmc.submit.exception;

public class CoreCaseDataStoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CoreCaseDataStoreException(String message) {
        super(message);
    }

    public CoreCaseDataStoreException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
