package com.busbooking.service;

import com.busbooking.dao.UserDAO;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public boolean login(String email, String password) {
        try {
            return userDAO.login(email, password);
        } catch (Exception e) {
            return false;
        }
    }
}
