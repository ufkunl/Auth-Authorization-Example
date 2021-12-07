package com.ufkunl.authandauthorizationtemplate.exception;

import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
public class GeneralAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final RestResponseCode restResponseCode;

    public GeneralAppException(RestResponseCode restResponseCode) {
        super(restResponseCode.getMessage());
        this.restResponseCode = restResponseCode;
    }

    public RestResponseCode getRestResponseCode() {
        return restResponseCode;
    }
}

