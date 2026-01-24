package com.busbooking.service;

import com.busbooking.dao.UserDAO;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public AuthResult login(String email, String password) {

        try {
            com.busbooking.model.User user = userDAO.authenticate(email, password);

            if (user != null) {
                return new AuthResult(true, user.getUserId(), user.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AuthResult(false, -1, null);
    }

    // ===== RESULT WRAPPER =====
    public static class AuthResult {
        public final boolean success;
        public final int userId;
        public final String userName;

        public AuthResult(boolean success, int userId, String userName) {
            this.success = success;
            this.userId = userId;
            this.userName = userName;
        }
    }
}
