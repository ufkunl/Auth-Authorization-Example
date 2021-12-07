package com.ufkunl.authandauthorizationtemplate.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

public record RestResponseCode(String code, String message) implements Serializable {

    public static final RestResponseCode SUCCESS = new RestResponseCode("0", "result.info.success");
    public static final RestResponseCode REFRESH_NOT_FOUND = new RestResponseCode("00001", "refresh.not.found");
    public static final RestResponseCode USERNAME_OR_PASSWORD_NOT_FOUND = new RestResponseCode("00002", "username.or.password.not.found");
    public static final RestResponseCode USERNAME_OR_EMAIL_EXIST = new RestResponseCode("00003", "username.or.email.exist");

    @JsonValue
    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
