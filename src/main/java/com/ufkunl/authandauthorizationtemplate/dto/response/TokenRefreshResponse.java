package com.ufkunl.authandauthorizationtemplate.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenRefreshResponse extends BaseResponse{
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
