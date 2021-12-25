package com.ufkunl.authandauthorizationtemplate.dto.response;

import com.ufkunl.authandauthorizationtemplate.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends BaseResponse{

    private String id;
    private String userName;
    private String email;
    private List<RoleResponse> roles;

}
