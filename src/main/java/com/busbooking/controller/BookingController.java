package com.busbooking.controller;

import com.busbooking.exception.BookingException;
import com.busbooking.service.BookingService;
import com.busbooking.service.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BookingController {

    @FXML
    private Label bookingIdLabel;
    @FXML
    private Label busNumberLabel;
    @FXML
    private Label routeLabel;
    @FXML
    private Label seatNumberLabel;
    @FXML
    private Label travelDateLabel;
    @FXML
    private Label fareLabel;

    private final BookingService bookingService = new BookingService();

    private static final int FARE_PER_SEAT = 400;

    // ===== MAIN ENTRY POINT (multi-seat) =====
    public void confirmBooking(int busId, String busNumber,
            String source, String destination,
            List<Integer> seatNumbers, LocalDate travelDate) {
        try {
            int userId = SessionService.getUserId();
            List<Integer> bookingIds = bookingService.bookSeats(
                    userId, busId, seatNumbers, travelDate);

            // Format booking IDs
            String bookingIdText = bookingIds.stream()
                    .map(id -> "BK-" + String.format("%05d", id))
                    .collect(Collectors.joining(", "));

            // Format seat numbers
            String seatText = seatNumbers.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            // Calculate total fare
            int totalFare = seatNumbers.size() * FARE_PER_SEAT;

            // Populate confirmation details
            bookingIdLabel.setText(bookingIdText);
            busNumberLabel.setText(busNumber);
            routeLabel.setText(source + " → " + destination);
            seatNumberLabel.setText(seatText + "  (" + seatNumbers.size() + " seat"
                    + (seatNumbers.size() > 1 ? "s" : "") + ")");
            travelDateLabel.setText(travelDate.format(
                    DateTimeFormatter.ofPattern("dd MMM yyyy")));
            fareLabel.setText("₹ " + totalFare);

        } catch (BookingException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            System.err.println("Booking error: " + e.getMessage());
            showError("Booking failed. Please try again.");
        }
    }

    // ===== BOOK ANOTHER =====
    @FXML
    private void handleBookAnother() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) bookingIdLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error navigating to dashboard: " + e.getMessage());
        }
    }

    // ===== EXIT =====
    @FXML
    private void handleExit() {
        Stage stage = (Stage) bookingIdLabel.getScene().getWindow();
        stage.close();
    }

    // ===== HELPERS =====
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Booking Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
