package com.ufkunl.authandauthorizationtemplate.service.impl;

import com.ufkunl.authandauthorizationtemplate.dto.request.UserRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.UserResponse;
import com.ufkunl.authandauthorizationtemplate.entity.User;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import com.ufkunl.authandauthorizationtemplate.exception.GeneralAppException;
import com.ufkunl.authandauthorizationtemplate.mapper.UserResponseMapper;
import com.ufkunl.authandauthorizationtemplate.repository.RoleRepository;
import com.ufkunl.authandauthorizationtemplate.repository.UserRepository;
import com.ufkunl.authandauthorizationtemplate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserResponseMapper userResponseMapper;

    /**
     * <p>This method can return created user
     * it can control that there is same user in db. it can encode user password.
     * </p>
     * @param userRequest parameter that have user info
     * @return created user
     * @since 1.0
     */
    @Override
    public UserResponse createUser(UserRequest userRequest){
        boolean userExist = userRepository.existsByUserNameOrEmail(userRequest.getUsername(), userRequest.getEmail());
        if (userExist) {
            throw new GeneralAppException(RestResponseCode.USERNAME_OR_EMAIL_EXIST);
        }
        User user = new User();
        user.setUserName(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user = userRepository.save(user);
        return userResponseMapper.entityToDto(user);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = (List<User>) userRepository.findAll();
        return userResponseMapper.entityListToDtoList(users);
    }

}
