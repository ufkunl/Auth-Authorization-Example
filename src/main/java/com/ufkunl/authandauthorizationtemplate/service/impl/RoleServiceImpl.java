package com.ufkunl.authandauthorizationtemplate.service.impl;

import com.ufkunl.authandauthorizationtemplate.dto.request.RoleRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.RoleResponse;
import com.ufkunl.authandauthorizationtemplate.entity.Role;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.mapper.RoleMapper;
import com.ufkunl.authandauthorizationtemplate.repository.RoleRepository;
import com.ufkunl.authandauthorizationtemplate.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        boolean userExist = roleRepository.existsByRoleName(roleRequest.getRoleName());
        if (userExist) {
            throw new GeneralAppException(RestResponseCode.USERNAME_OR_EMAIL_EXIST);
        }
        Role role = new Role();
        role.setRoleName(roleRequest.getRoleName());
        role = roleRepository.save(role);
        return roleMapper.entityToDto(role);
    }

    @Override
    public List<RoleResponse> getAllRole() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roleMapper.entityListToDtoList(roles);
    }
}
