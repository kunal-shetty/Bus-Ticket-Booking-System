package com.busbooking.service;

public class SessionService {

    private static int currentUserId = -1;
    private static String currentUserName;

    public static void startSession(int userId, String userName) {
        currentUserId = userId;
        currentUserName = userName;
    }

    public static void endSession() {
        currentUserId = -1;
        currentUserName = null;
    }

    public static int getUserId() {
        return currentUserId;
    }

    public static String getUserName() {
        return currentUserName;
    }

    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }
}
