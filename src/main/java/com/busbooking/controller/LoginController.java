package com.busbooking.controller;

import com.busbooking.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // ===== SERVICE =====
    private final AuthService authService = new AuthService();

    // ===== LOGIN HANDLER =====
    @FXML
    private void handleLogin() {

        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter email and password");
            return;
        }

        AuthService.AuthResult result = authService.login(email, password);

        if (result.success) {
            loadDashboard(result.userId, result.userName);
        } else {
            messageLabel.setText("Invalid email or password");
        }
    }

    // ===== LOAD DASHBOARD =====
    private void loadDashboard(int userId, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUserSession(userId, userName);

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());

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
