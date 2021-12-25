package com.ufkunl.authandauthorizationtemplate.service;

import com.ufkunl.authandauthorizationtemplate.dto.request.UserRequest;
import com.ufkunl.authandauthorizationtemplate.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUser();

}
