package com.ufkunl.authandauthorizationtemplate.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshTokenRequest extends BaseRequest{

    @NotBlank
    private String refreshToken;

}
