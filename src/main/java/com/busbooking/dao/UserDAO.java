package com.busbooking.dao;

import com.busbooking.model.User;
import com.busbooking.util.DBConnection;

import java.sql.*;

public class UserDAO {

    public boolean login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        PreparedStatement ps =
                DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
