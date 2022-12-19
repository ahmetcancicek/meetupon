package com.meetupon.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotBlank(message = "client.requiredField")
    private String username;

    @NotEmpty(message = "client.requiredField")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
