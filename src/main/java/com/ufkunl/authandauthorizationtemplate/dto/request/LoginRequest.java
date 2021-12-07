package com.ufkunl.authandauthorizationtemplate.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequest extends BaseRequest{

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
