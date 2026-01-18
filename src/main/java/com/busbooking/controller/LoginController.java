package com.busbooking.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // ===== DATABASE CONFIG =====
    private static final String URL =
            "jdbc:mysql://localhost:3306/bus_ticket_booking_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kunal1234";

    // ===== LOGIN HANDLER =====
    @FXML
    private void handleLogin() {

        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter email and password");
            return;
        }

        String sql = """
            SELECT user_id, name
            FROM users
            WHERE email = ? AND password = ?
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("name");

                loadDashboard(userId, userName);
            } else {
                messageLabel.setText("Invalid email or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Database error. Try again.");
        }
    }

    // ===== LOAD DASHBOARD =====
    private void loadDashboard(int userId, String userName) {
        try {
            FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUserSession(userId, userName);

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Failed to load dashboard");
        }
    }

    // ===== REGISTER HANDLER (PLACEHOLDER NAVIGATION) =====
    @FXML
    private void handleRegister() {
        messageLabel.setText("Registration screen not implemented yet");
    }
}
