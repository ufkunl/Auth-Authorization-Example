package com.ufkunl.authandauthorizationtemplate.dto;

import com.ufkunl.authandauthorizationtemplate.dto.response.BaseResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RestResponse extends BaseResponse {
    private RestResponseCode resultCode;
    private String resultMessage;
    private transient Object data;

    public RestResponse(RestResponseCode resultCode, Object data) {
        this.resultCode = resultCode;
        this.resultMessage = resultCode.getMessage();
        this.data = data;
    }
}
