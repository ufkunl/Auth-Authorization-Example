package com.ufkunl.authandauthorizationtemplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufkunl.authandauthorizationtemplate.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode.USERNAME_OR_PASSWORD_NOT_FOUND;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointJwt.class);

    @Autowired
    ResponseUtils responseUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseUtils.createResponse(null, USERNAME_OR_PASSWORD_NOT_FOUND));

    }
}
