package uk.gov.hmcts.reform.cmc.submit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException;

@ControllerAdvice
public class ApplicationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    public ApplicationResponseEntityExceptionHandler(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getBindingResult().getAllErrors(), headers, status, request);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(((ApplicationException) ex).getMessage(),
                                    new HttpHeaders(),
                                    HttpStatus.NOT_FOUND);
    }

}