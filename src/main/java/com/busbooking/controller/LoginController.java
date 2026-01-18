package com.busbooking.controller;

import com.busbooking.service.AuthService;

public class LoginController {

    private final AuthService authService = new AuthService();

    public void handleLogin(String email, String password) {
        if (authService.login(email, password)) {
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid credentials");
        }
    }
}
