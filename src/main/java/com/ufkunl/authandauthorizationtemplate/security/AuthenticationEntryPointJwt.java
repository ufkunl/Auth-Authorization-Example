package com.ufkunl.authandauthorizationtemplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode.ACCESS_TOKEN_INVALID;
import static com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode.USERNAME_OR_PASSWORD_NOT_FOUND;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointJwt.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final ObjectMapper mapper = new ObjectMapper();
        String message;
        if (authException.getMessage().equals("Bad credentials")) {
            message = messageSource.getMessage(USERNAME_OR_PASSWORD_NOT_FOUND.getMessage(), null, LocaleContextHolder.getLocale());
            mapper.writeValue(response.getOutputStream(), new RestResponse<>(USERNAME_OR_PASSWORD_NOT_FOUND.getCode(), message, null));
        } else {
            message = messageSource.getMessage(ACCESS_TOKEN_INVALID.getMessage(), null, LocaleContextHolder.getLocale());
            mapper.writeValue(response.getOutputStream(), new RestResponse<>(ACCESS_TOKEN_INVALID.getCode(), message, null));
        }
    }
}
