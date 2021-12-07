package com.ufkunl.authandauthorizationtemplate.util;

import com.ufkunl.authandauthorizationtemplate.dto.RestResponse;
import com.ufkunl.authandauthorizationtemplate.enums.RestResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;



/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Component
public class ResponseUtils {

    @Autowired
    private MessageSource messageSource;

    /**
     * <p>This method set message by language from properties
     * </p>
     * @param object,code parameter
     * @return RestResponse
     * @since 1.0
     */
    public RestResponse createResponse(Object object,RestResponseCode code){
        RestResponse response = new RestResponse(code,object);
        String message = messageSource.getMessage(response.getResultMessage(),null, LocaleContextHolder.getLocale());
        response.setResultMessage(message);
        return response;
    }

}
