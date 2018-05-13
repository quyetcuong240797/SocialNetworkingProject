package com.ducnd.security;

import com.ducnd.Constants;
import com.ducnd.security.exception.AuthenticationUsernamePasswordInvalidException;
import com.ducnd.security.login.AjaxAuthenLogin;
import com.ducnd.security.regiseter.AjaxAuthenRegister;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.tools.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ducnd on 6/25/17.
 */
public class AjaxLoginRegisterProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private AjaxAuthenticationLoginRegisterSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    private ObjectMapper objectMapper;

    public AjaxLoginRegisterProcessingFilter(RequestMatcher matcher, AjaxAuthenticationLoginRegisterSuccessHandler successHandler,
                                             AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(matcher);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        LoginRequest loginRequest = objectMapper.readValue(httpServletRequest.getReader(), LoginRequest.class);
        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationUsernamePasswordInvalidException("invalid param for login");
        }
        if (httpServletRequest.getRequestURI().equals(Constants.ENPOINT_LOGIN)) {
            AjaxAuthenLogin authenLogin = new AjaxAuthenLogin(loginRequest.getUsername(), loginRequest.getPassword());
            return getAuthenticationManager().authenticate(authenLogin);
        } else {
            if (loginRequest.getPassword().length() < Constants.MIN_LEHGTH_PASSWORD) {
                throw new AuthenticationServiceException("Leght password must than 6 character");
            }
            AjaxAuthenRegister register = new AjaxAuthenRegister(loginRequest.getUsername(), loginRequest.getPassword());
            return getAuthenticationManager().authenticate(register);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }


}
