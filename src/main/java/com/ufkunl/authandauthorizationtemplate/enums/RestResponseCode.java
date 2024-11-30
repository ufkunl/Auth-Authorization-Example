package com.ufkunl.authandauthorizationtemplate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.io.Serializable;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
public record RestResponseCode(String code, String message) implements Serializable {

    public static final RestResponseCode SUCCESS = new RestResponseCode("0", "result.info.success");
    public static final RestResponseCode ERROR = new RestResponseCode("-1", "result.info.unknown.error");
    public static final RestResponseCode REFRESH_NOT_FOUND = new RestResponseCode("10001", "refresh.not.found");
    public static final RestResponseCode ACCESS_TOKEN_INVALID = new RestResponseCode("10002", "access.token.not.found");
    public static final RestResponseCode USERNAME_OR_PASSWORD_NOT_FOUND = new RestResponseCode("10003", "username.or.password.not.found");
    public static final RestResponseCode USERNAME_OR_EMAIL_EXIST = new RestResponseCode("10004", "username.or.email.exist");
    public static final RestResponseCode ACCESS_DENIED_ERROR = new RestResponseCode("10005", "access.denied.error");

    @JsonCreator
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public String getMessage() {
        return this.message;
    }
}
