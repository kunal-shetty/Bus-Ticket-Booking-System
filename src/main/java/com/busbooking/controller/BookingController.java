package com.busbooking.controller;

import com.busbooking.service.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BookingController {

    // ===== FXML LABELS =====
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

    // ===== SERVICE =====
    private final BookingService bookingService = new BookingService();

    // ===== DATA PASSED FROM PREVIOUS SCREEN =====
    private int userId;
    private int busId;
    private int seatNumber;
    private LocalDate travelDate;

    // ===== INITIALIZATION =====
    @FXML
    public void initialize() {
        // Nothing here on purpose.
    }

    // ===== MAIN ENTRY POINT FROM SeatSelectionController =====
    public void confirmBooking(int userId,
            int busId,
            int seatNumber,
            LocalDate travelDate) {

        this.userId = userId;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;

        try {
            // Call service to book ticket
            int bookingId = bookingService.bookSeat(userId, busId, seatNumber, travelDate);

            if (bookingId > 0) {
                // Success
                bookingIdLabel.setText("BK" + bookingId);
                seatNumberLabel.setText(String.valueOf(seatNumber));
                travelDateLabel.setText(travelDate.toString());
                fareLabel.setText("₹400");

                // Note: In a real application, we would fetch and display the bus details here.
                // For now, the booking ID and Seat Number are sufficient confimation.

            } else {
                showError("Booking failed. Seat might be already booked.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Booking failed. Please try again.");
        }
    }

    // ===== UI ACTIONS =====

    @FXML
    private void handleBookAnother() {
        closeWindow();
        // Navigation back to dashboard handled by caller
    }

    @FXML
    private void handleExit() {
        closeWindow();
        System.exit(0);
    }

    // ===== UTIL =====
    private void closeWindow() {
        Stage stage = (Stage) bookingIdLabel.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Booking Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
