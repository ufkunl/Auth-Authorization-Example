package com.ufkunl.authandauthorizationtemplate.controller;


import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.UserRequest;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.service.UserService;
import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseUtils responseUtils;

    @PostMapping()
    public ResponseEntity<RestResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(responseUtils.createResponse(userService.createUser(userRequest), RestResponseCode.SUCCESS));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RestResponse> getAllUser() {
        return ResponseEntity.ok(responseUtils.createResponse(userService.getAllUser(), RestResponseCode.SUCCESS));
    }

}
