package com.ufkunl.authandauthorizationtemplate.controller;


import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.dto.request.RoleRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.RoleResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN')")
    public ResponseEntity<RestResponse<RoleResponse>> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, roleService.createRole(roleRequest)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN')")
    public ResponseEntity<RestResponse<List<RoleResponse>>> getAllRole() {
        return ResponseEntity.ok().body(new RestResponse<>(RestResponseCode.SUCCESS, roleService.getAllRole()));
    }
}
