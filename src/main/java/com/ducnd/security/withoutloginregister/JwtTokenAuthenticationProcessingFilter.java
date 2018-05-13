package com.ducnd.security.withoutloginregister;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
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
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationFailureHandler failureHandler;

    public JwtTokenAuthenticationProcessingFilter(RequestMatcher matcher,
                                                  AuthenticationFailureHandler failureHandler, ObjectMapper objectMapper) {
        super(matcher);
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String tokenPayload = httpServletRequest.getHeader("X-Authorization");
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(tokenPayload));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("destroy...........................");
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        System.out.println("afterPropertiesSet...........................");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("afterPropertiesSet...........................");
        return super.requiresAuthentication(request, response);

    }


}
