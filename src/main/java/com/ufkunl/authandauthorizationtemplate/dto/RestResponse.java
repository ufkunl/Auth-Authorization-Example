package com.ufkunl.authandauthorizationtemplate.dto;

import com.ufkunl.authandauthorizationtemplate.dto.response.BaseResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Data
public class RestResponse<T> {
    private String code;
    private String message;
    private transient T data;

    public RestResponse(RestResponseCode restResponseCode, T data) {
        this.code = restResponseCode.getCode();
        this.message = restResponseCode.getMessage();
        this.data = data;
    }

    public RestResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
