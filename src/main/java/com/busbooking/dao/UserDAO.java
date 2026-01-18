package com.busbooking.dao;

import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // ===== AUTHENTICATE USER =====
    public ResultSet authenticate(String email, String password) {

        String sql = """
            SELECT user_id, name
            FROM users
            WHERE email = ? AND password = ?
        """;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
