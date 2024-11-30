package com.ufkunl.authandauthorizationtemplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode.ACCESS_DENIED_ERROR;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
        logger.error("Access denied error: {}", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        final ObjectMapper mapper = new ObjectMapper();
        String message = messageSource.getMessage(ACCESS_DENIED_ERROR.getMessage(), null, LocaleContextHolder.getLocale());
        mapper.writeValue(response.getOutputStream(), new RestResponse<>(ACCESS_DENIED_ERROR.getCode(), message, null));
    }
}
