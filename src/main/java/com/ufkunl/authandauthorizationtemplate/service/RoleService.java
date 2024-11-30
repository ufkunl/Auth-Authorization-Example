package com.ufkunl.authandauthorizationtemplate.service;

import com.ufkunl.authandauthorizationtemplate.dto.request.RoleRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> getAllRole();

}
