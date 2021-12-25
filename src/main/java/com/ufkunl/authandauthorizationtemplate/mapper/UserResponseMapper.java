package com.ufkunl.authandauthorizationtemplate.mapper;

import com.ufkunl.authandauthorizationtemplate.dto.response.RoleResponse;
import com.ufkunl.authandauthorizationtemplate.dto.response.UserResponse;
import com.ufkunl.authandauthorizationtemplate.entity.Role;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserResponseMapper implements BaseMapper<User,UserResponse>{

    @Autowired
    RoleMapper roleMapper;

    @Override
    public User dtoToEntity(UserResponse userResponse) {
        Set<Role> roles = (Set<Role>) userResponse.getRoles().stream().map(role -> roleMapper.dtoToEntity(role)).toList();

        User user = new User();
        user.setEmail(userResponse.getEmail());
        user.setUserId(userResponse.getId());
        user.setUserName(userResponse.getUserName());
        user.setRoles(roles);
        return user;
    }

    @Override
    public UserResponse entityToDto(User entity) {
        List<RoleResponse> roles = entity.getRoles().stream().map(role -> roleMapper.entityToDto(role)).toList();

        UserResponse response = new UserResponse();
        response.setEmail(entity.getEmail());
        response.setId(entity.getUserId());
        response.setUserName(entity.getUserName());
        response.setRoles(roles);
        return response;
    }

    @Override
    public List<UserResponse> entityListToDtoList(List<User> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }

    @Override
    public List<User> dtoListToEntityList(List<UserResponse> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }
}
