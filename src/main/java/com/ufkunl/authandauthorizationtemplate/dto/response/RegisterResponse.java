package com.ufkunl.authandauthorizationtemplate.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterResponse extends BaseResponse{
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String id;
    private String username;
    private String email;
    private List<String> roles;

    public RegisterResponse(String accessToken, String refreshToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
