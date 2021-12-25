package com.ufkunl.authandauthorizationtemplate.mapper;

import com.ufkunl.authandauthorizationtemplate.dto.response.RoleResponse;
import com.ufkunl.authandauthorizationtemplate.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper implements BaseMapper<Role, RoleResponse>{
    @Override
    public Role dtoToEntity(RoleResponse roleResponse) {
        Role role = new Role();
        role.setRoleName(roleResponse.getRoleName());
        return role;
    }

    @Override
    public RoleResponse entityToDto(Role entity) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setRoleName(entity.getRoleName());
        return roleResponse;
    }

    @Override
    public List<RoleResponse> entityListToDtoList(List<Role> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }

    @Override
    public List<Role> dtoListToEntityList(List<RoleResponse> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }
}
