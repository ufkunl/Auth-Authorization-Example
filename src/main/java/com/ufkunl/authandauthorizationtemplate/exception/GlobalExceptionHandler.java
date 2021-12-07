package com.ufkunl.authandauthorizationtemplate.exception;

import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ResponseUtils responseUtils;

    @ExceptionHandler(value = {GeneralAppException.class})
    protected ResponseEntity<Object> handleRuntimeProductException(GeneralAppException ex, WebRequest request) {
        Object response = responseUtils.createResponse(null, ex.getRestResponseCode());
        return handleExceptionInternal(ex,response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
