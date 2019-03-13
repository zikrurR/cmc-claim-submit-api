package uk.gov.hmcts.reform.cmc.ccd.mapper.exception;

public class MappingException extends RuntimeException {

    public MappingException() {
        super("CMC CCD mapping exception!");
    }

    public MappingException(String message) {
        super("CMC CCD mapping exception. " + message);
    }

    public MappingException(String message, Throwable e) {
        super(message, e);
    }
}
