package com.busbooking.service;

import com.busbooking.dao.UserDAO;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    // ===== LOGIN =====
    public AuthResult login(String email, String password) {
        try {
            com.busbooking.model.User user = userDAO.authenticate(email, password);

            if (user != null) {
                return new AuthResult(true, user.getUserId(), user.getName(), null);
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return new AuthResult(false, -1, null, "An error occurred. Please try again.");
        }

        return new AuthResult(false, -1, null, "Invalid email or password.");
    }

    // ===== REGISTER =====
    public AuthResult register(String name, String email, String password) {
        try {
            if (userDAO.emailExists(email)) {
                return new AuthResult(false, -1, null, "Email already registered.");
            }

            boolean success = userDAO.registerUser(name, email, password);
            if (success) {
                return new AuthResult(true, -1, name, null);
            }
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
        }

        return new AuthResult(false, -1, null, "Registration failed. Please try again.");
    }

    // ===== RESULT WRAPPER =====
    public static class AuthResult {
        public final boolean success;
        public final int userId;
        public final String userName;
        public final String errorMessage;

        public AuthResult(boolean success, int userId, String userName, String errorMessage) {
            this.success = success;
            this.userId = userId;
            this.userName = userName;
            this.errorMessage = errorMessage;
        }
    }
}
