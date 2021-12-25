package com.ufkunl.authandauthorizationtemplate.controller;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.LoginRequest;
import com.ufkunl.authandauthorizationtemplate.dto.request.RefreshTokenRequest;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.repository.RoleRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import com.ufkunl.authandauthorizationtemplate.service.AuthService;
import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    AuthService authService;

    @Autowired
    ResponseUtils responseUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<RestResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(responseUtils.createResponse(authService.authenticate(loginRequest), RestResponseCode.SUCCESS));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<RestResponse> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) throws NotFoundException {
        return ResponseEntity.ok(responseUtils.createResponse(authService.refreshToken(request),RestResponseCode.SUCCESS));
    }

}
