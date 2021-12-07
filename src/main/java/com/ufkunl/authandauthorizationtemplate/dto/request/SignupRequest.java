package com.ufkunl.authandauthorizationtemplate.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignupRequest  extends BaseRequest{

    private String username;

    private String email;

    private Set<String> role;

    private String password;

}