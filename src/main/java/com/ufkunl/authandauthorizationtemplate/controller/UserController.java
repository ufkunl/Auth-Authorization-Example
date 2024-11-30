package com.ufkunl.authandauthorizationtemplate.controller;


import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.UserRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.UserResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN')")
    public ResponseEntity<RestResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, userService.createUser(userRequest)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN')")
    public ResponseEntity<RestResponse<List<UserResponse>>> getAllUser() {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, userService.getAllUser()));
    }
}
