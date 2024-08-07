package com.ufkunl.authandauthorizationtemplate.dto.request;

import com.ufkunl.authandauthorizationtemplate.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends BaseRequest{

    private String username;

    private String email;

    private Set<Role> role;

    private String password;

}