package com.ufkunl.authandauthorizationtemplate.controller;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.LoginResponse;
import com.ufkunl.authandauthorizationtemplate.dto.response.TokenRefreshResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, authService.authenticate(loginRequest)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RestResponse<TokenRefreshResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, authService.refreshToken(request)));
    }
}
