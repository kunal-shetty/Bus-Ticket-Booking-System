package com.busbooking.controller;

import com.busbooking.service.SessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    // ===== CALLED AFTER FXML LOADS =====
    @FXML
    public void initialize() {
        if (SessionService.isLoggedIn()) {
            welcomeLabel.setText("Welcome, " + SessionService.getUserName());
        }
    }

    // ===== KEPT FOR BACKWARDS COMPATIBILITY =====
    public void setUserSession(int userId, String userName) {
        SessionService.startSession(userId, userName);
        welcomeLabel.setText("Welcome, " + userName);
    }

    // ===== NAVIGATION HANDLERS =====

    @FXML
    private void handleSearchBuses(ActionEvent event) {
        navigate(event, "/fxml/search_bus.fxml");
    }

    @FXML
    private void handleBookTicket(ActionEvent event) {
        navigate(event, "/fxml/search_bus.fxml");
    }

    @FXML
    private void handleBookingHistory(ActionEvent event) {
        navigate(event, "/fxml/booking_history.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SessionService.endSession();
        navigate(event, "/fxml/login.fxml");
    }

    // ===== COMMON NAVIGATION METHOD =====
    private void navigate(ActionEvent event, String fxmlPath) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Navigation error: " + e.getMessage());
        }
    }
}
