package com.kidneystone.dto;

public class AuthResponse {

    private String message;
    private String token;
    private Long userId;
    private String username;
    private String email;

    public AuthResponse() {
    }

    public AuthResponse(String message, String token, Long userId, String username, String email) {
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}