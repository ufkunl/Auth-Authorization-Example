package com.ufkunl.authandauthorizationtemplate.exception;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode.ERROR;

/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<RestResponseCode>> handleException(Exception ex, Locale locale) {
        if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            throw (RuntimeException) ex;
        }
        log.error("Unexpected Error : {}", ex.getMessage());
        String message = messageSource.getMessage(ERROR.getMessage(), null, locale);
        return ResponseEntity.ok().body(new RestResponse<>(ERROR.code(), message, null));
    }

    @ExceptionHandler(GeneralAppException.class)
    public ResponseEntity<RestResponse<RestResponseCode>> handleGeneralAppException(GeneralAppException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getRestResponseCode().getMessage(), null, locale);
        return ResponseEntity.ok().body(new RestResponse<>(ex.getRestResponseCode().code(), message, null));
    }
}
