package com.ducnd.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by ducnd on 6/26/17.
 */
public class JwtExpiredTokenException extends AuthenticationException {
    private String token;
    public JwtExpiredTokenException(String msg, String token, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
