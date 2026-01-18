package com.busbooking.controller;

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

    // This should be set after login
    private int loggedInUserId;
    private String loggedInUserName;

    // ===== CALLED AFTER LOGIN =====
    public void setUserSession(int userId, String userName) {
        this.loggedInUserId = userId;
        this.loggedInUserName = userName;
        welcomeLabel.setText("Welcome, " + userName);
    }

    // ===== NAVIGATION HANDLERS =====

    @FXML
    private void handleSearchBuses(ActionEvent event) {
        navigate(event, "/fxml/search_bus.fxml");
    }

    @FXML
    private void handleBookTicket(ActionEvent event) {
        // Booking always starts with searching buses
        navigate(event, "/fxml/search_bus.fxml");
    }

    @FXML
    private void handleBookingHistory(ActionEvent event) {
        navigate(event, "/fxml/booking_history.fxml");
        // booking_history.fxml can be implemented next
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        navigate(event, "/fxml/login.fxml");
    }

    // ===== COMMON NAVIGATION METHOD =====
    private void navigate(ActionEvent event, String fxmlPath) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene()
                    .getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass session to next controller if needed
            Object controller = loader.getController();

            if (controller instanceof SearchBusController searchBusController) {
                searchBusController.setUserSession(loggedInUserId);
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
