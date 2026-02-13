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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeatSelectionController {

    @FXML
    private GridPane seatGrid;
    @FXML
    private Label busInfoLabel;
    @FXML
    private Label selectedSeatLabel;

    private final SeatService seatService = new SeatService();

    private int busId;
    private String busNumber;
    private String source;
    private String destination;
    private final List<Integer> selectedSeats = new ArrayList<>();

    // ===== CALLED FROM SearchBusController =====
    public void setBookingContext(int busId, String busNumber,
            String source, String destination) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;

        busInfoLabel.setText("Bus: " + busNumber + "  |  " + source + " → " + destination);
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
            seatBtn.setMinSize(50, 45);
            seatBtn.setPrefSize(55, 45);

            if (isBooked) {
                seatBtn.getStyleClass().add("seat-booked");
                seatBtn.setDisable(true);
            } else {
                seatBtn.getStyleClass().add("seat-available");
                seatBtn.setOnAction(e -> toggleSeat(seatBtn, seatNumber));
            }

            seatGrid.add(seatBtn, col, row);
            col++;

            // Add aisle gap after 2nd seat
            if (col == 2) {
                Label aisle = new Label("");
                aisle.setMinWidth(30);
                seatGrid.add(aisle, col, row);
                col++;
            }
            if (col == 5) { // 2 seats + aisle + 2 seats = 5 columns
                col = 0;
                row++;
            }
        }
    }

    // ===== TOGGLE SEAT SELECTION (multi-select) =====
    private void toggleSeat(Button clickedButton, int seatNumber) {
        if (selectedSeats.contains(seatNumber)) {
            // Deselect
            selectedSeats.remove(Integer.valueOf(seatNumber));
            clickedButton.getStyleClass().remove("seat-selected");
            clickedButton.getStyleClass().add("seat-available");
        } else {
            // Select
            selectedSeats.add(seatNumber);
            clickedButton.getStyleClass().remove("seat-available");
            clickedButton.getStyleClass().add("seat-selected");
        }

        updateSelectionLabel();
    }

    private void updateSelectionLabel() {
        if (selectedSeats.isEmpty()) {
            selectedSeatLabel.setText("Select a seat to continue");
            selectedSeatLabel.getStyleClass().removeAll("highlight-label", "error-label");
            selectedSeatLabel.getStyleClass().add("info-label");
        } else {
            String seatList = selectedSeats.stream()
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            String label = selectedSeats.size() == 1
                    ? "Selected Seat: " + seatList
                    : "Selected " + selectedSeats.size() + " Seats: " + seatList;

            selectedSeatLabel.setText(label);
            selectedSeatLabel.getStyleClass().removeAll("error-label", "info-label");
            selectedSeatLabel.getStyleClass().add("highlight-label");
        }
    }

    // ===== CONFIRM BOOKING =====
    @FXML
    private void handleConfirmBooking() {
        if (selectedSeats.isEmpty()) {
            selectedSeatLabel.setText("⚠ Please select at least one seat");
            selectedSeatLabel.getStyleClass().add("error-label");
            return;
        }

        try {
            List<Integer> sortedSeats = selectedSeats.stream().sorted().collect(Collectors.toList());

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/booking_confirmation.fxml"));
            Parent root = loader.load();

            BookingController controller = loader.getController();
            controller.confirmBooking(
                    busId,
                    busNumber,
                    source,
                    destination,
                    sortedSeats,
                    LocalDate.now());

            Stage stage = (Stage) seatGrid.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error loading booking confirmation: " + e.getMessage());
        }
    }

    // ===== BACK TO SEARCH =====
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_bus.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) seatGrid.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
        }
    }
}
