package com.ducnd.security;

/**
 * Created by ducnd on 6/25/17.
 */
public class UserContext {
    private String username;
    private String token;

    public UserContext(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken(){
        return token;
    }
}
