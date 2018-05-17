package com.ducnd.model.response.response;

public class LoginResponse {
    int id;
    String token;


    public LoginResponse(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public LoginResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
