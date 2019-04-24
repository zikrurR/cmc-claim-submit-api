package uk.gov.hmcts.reform.cmc.submit.mapper.exception;

public class MappingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MappingException() {
        super("CMC CCD mapping exception!");
    }

    public MappingException(String message) {
        super("CMC CCD mapping exception. " + message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
