package com.ufkunl.authandauthorizationtemplate.controller;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.SignupRequest;
import com.ufkunl.authandauthorizationtemplate.service.AuthService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RestResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws NotFoundException {
        return authService.authenticate(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
       return authService.registerUser(signUpRequest);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<RestResponse> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) throws NotFoundException {
        return authService.refreshToken(request);
    }

}
