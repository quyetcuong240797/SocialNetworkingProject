package com.ducnd.security;

import com.ducnd.Constants;
import com.ducnd.model.response.ResponseUtils;
import com.ducnd.security.exception.AuthenticationUsernamePasswordInvalidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ducnd on 6/25/17.
 */
public class AjaxAuthenticationLoginRegisterFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public AjaxAuthenticationLoginRegisterFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (e instanceof AuthenticationUsernamePasswordInvalidException) {
            objectMapper.writeValue(response.getWriter(), ResponseUtils.getBaseResponse(Constants.STATUS_CODE_USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        } else {
            objectMapper.writeValue(response.getWriter(), ResponseUtils.getBaseResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        }

    }
}
