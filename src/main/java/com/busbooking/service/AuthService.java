package com.busbooking.service;

import com.busbooking.dao.UserDAO;

import java.sql.ResultSet;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public AuthResult login(String email, String password) {

        try {
            ResultSet rs = userDAO.authenticate(email, password);

            if (rs != null && rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");

                return new AuthResult(true, userId, name);
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
