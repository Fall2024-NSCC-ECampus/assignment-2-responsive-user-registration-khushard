package com.example.userregistration.service;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for login requests.
 * Went this route because I was having issues getting the login to work properly without using all the information in the User object.(email).
 * */
public class LoginDTO {
    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    public @NotBlank(message = "Username is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank(message = "Username is required") String userName) {
        this.userName = userName;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }
}
