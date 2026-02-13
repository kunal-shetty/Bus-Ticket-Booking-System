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

public class RegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label messageLabel;

    private final AuthService authService = new AuthService();

    // ===== REGISTER HANDLER =====
    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields", true);
            return;
        }

        if (name.length() < 2) {
            showMessage("Name must be at least 2 characters", true);
            return;
        }

        if (!isValidEmail(email)) {
            showMessage("Please enter a valid email address", true);
            return;
        }

        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters", true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match", true);
            return;
        }

        AuthService.AuthResult result = authService.register(name, email, password);

        if (result.success) {
            showMessage("Registration successful! Redirecting to login...", false);

            // Navigate to login after a brief delay
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                    javafx.util.Duration.seconds(1.5));
            pause.setOnFinished(e -> navigateToLogin());
            pause.play();
        } else {
            showMessage(result.errorMessage, true);
        }
    }

    // ===== BACK TO LOGIN =====
    @FXML
    private void handleBackToLogin() {
        navigateToLogin();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error loading login screen: " + e.getMessage());
        }
    }

    // ===== HELPERS =====
    private void showMessage(String text, boolean isError) {
        messageLabel.setText(text);
        if (isError) {
            messageLabel.getStyleClass().removeAll("success-label");
            messageLabel.getStyleClass().add("error-label");
        } else {
            messageLabel.getStyleClass().removeAll("error-label");
            messageLabel.getStyleClass().add("success-label");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
