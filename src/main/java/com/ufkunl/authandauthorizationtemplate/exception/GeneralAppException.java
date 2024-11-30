package com.ufkunl.authandauthorizationtemplate.exception;

import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import lombok.Getter;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Getter
public class GeneralAppException extends RuntimeException {

    private final RestResponseCode restResponseCode;

    public GeneralAppException(RestResponseCode restResponseCode) {
        super(restResponseCode.getMessage());
        this.restResponseCode = restResponseCode;
    }
}

