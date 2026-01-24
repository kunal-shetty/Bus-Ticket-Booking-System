package com.busbooking.controller;

import com.busbooking.service.SeatService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Map;

public class SeatSelectionController {

    // ===== FXML =====
    @FXML
    private GridPane seatGrid;
    @FXML
    private Label busInfoLabel;
    @FXML
    private Label selectedSeatLabel;

    // ===== SERVICES =====
    private final SeatService seatService = new SeatService();

    // ===== SESSION / CONTEXT =====
    private int userId;
    private int busId;
    private String busNumber;
    private String source;
    private String destination;

    private Integer selectedSeatNumber = null;

    // ===== CALLED FROM SearchBusController =====
    public void setBookingContext(int userId,
            int busId,
            String busNumber,
            String source,
            String destination) {

        this.userId = userId;
        this.busId = busId;
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;

        busInfoLabel.setText(
                "Bus: " + busNumber + " | " + source + " → " + destination);

        loadSeatsFromDatabase();
    }

    // ===== LOAD SEATS =====
    private void loadSeatsFromDatabase() {

        seatGrid.getChildren().clear();

        Map<Integer, Boolean> seats = seatService.getSeatAvailability(busId);

        int col = 0;
        int row = 0;

        for (Map.Entry<Integer, Boolean> entry : seats.entrySet()) {
            int seatNumber = entry.getKey();
            boolean isBooked = entry.getValue();

            Button seatBtn = new Button(String.valueOf(seatNumber));
            seatBtn.setMinSize(45, 40);

            if (isBooked) {
                seatBtn.getStyleClass().add("seat-booked");
                seatBtn.setDisable(true);
            } else {
                seatBtn.getStyleClass().add("seat-available");
                seatBtn.setOnAction(e -> selectSeat(seatBtn, seatNumber));
            }

            seatGrid.add(seatBtn, col, row);
            col++;
            if (col == 4) { // 4 seats per row
                col = 0;
                row++;
            }
        }
    }

    // ===== SEAT SELECTION =====
    private void selectSeat(Button clickedButton, int seatNumber) {

        // Clear previous selection
        seatGrid.getChildren().forEach(node -> {
            if (node instanceof Button btn &&
                    btn.getStyleClass().contains("seat-selected")) {
                btn.getStyleClass().remove("seat-selected");
                btn.getStyleClass().add("seat-available");
            }
        });

        clickedButton.getStyleClass().remove("seat-available");
        clickedButton.getStyleClass().add("seat-selected");

        selectedSeatNumber = seatNumber;
        selectedSeatLabel.setText("Selected Seat: " + seatNumber);
    }

    // ===== CONFIRM BOOKING =====
    @FXML
    private void handleConfirmBooking() {

        if (selectedSeatNumber == null) {
            selectedSeatLabel.setText("Please select a seat first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/booking_confirmation.fxml"));
            Parent root = loader.load();

            BookingController controller = loader.getController();
            controller.confirmBooking(
                    userId,
                    busId,
                    selectedSeatNumber,
                    LocalDate.now());

            Stage stage = (Stage) seatGrid.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== BACK TO SEARCH =====
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_bus.fxml"));
            Parent root = loader.load();

            SearchBusController controller = loader.getController();
            controller.setUserSession(userId);

            Stage stage = (Stage) seatGrid.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
