package com.ufkunl.authandauthorizationtemplate.service;

import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.LoginResponse;
import com.ufkunl.authandauthorizationtemplate.dto.response.TokenRefreshResponse;

/**
 * Created by Ufuk UNAL on 07.12.2021
 */
public interface AuthService {

    LoginResponse authenticate(LoginRequest loginRequest);

    TokenRefreshResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
