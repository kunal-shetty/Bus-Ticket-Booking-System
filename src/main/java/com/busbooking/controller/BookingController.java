package com.busbooking.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class BookingController {

    // ===== FXML LABELS =====
    @FXML private Label bookingIdLabel;
    @FXML private Label busNumberLabel;
    @FXML private Label routeLabel;
    @FXML private Label seatNumberLabel;
    @FXML private Label travelDateLabel;
    @FXML private Label fareLabel;

    // ===== DATABASE CONFIG =====
    private static final String URL =
            "jdbc:mysql://localhost:3306/bus_ticket_booking_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kunal1234";

    // ===== DATA PASSED FROM PREVIOUS SCREEN =====
    private int userId;
    private int busId;
    private int seatNumber;
    private LocalDate travelDate;

    // ===== INITIALIZATION =====
    @FXML
    public void initialize() {
        // Nothing here on purpose.
        // Booking happens ONLY when data is passed properly.
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

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            conn.setAutoCommit(false);

            // 1️⃣ Check seat availability
            if (!isSeatAvailable(conn)) {
                showError("Seat already booked!");
                return;
            }

            // 2️⃣ Insert booking
            int bookingId = insertBooking(conn);

            // 3️⃣ Mark seat as booked
            markSeatBooked(conn);

            // 4️⃣ Fetch bus details
            loadBusDetails(conn);

            conn.commit();

            bookingIdLabel.setText("BK" + bookingId);
            seatNumberLabel.setText(String.valueOf(seatNumber));
            travelDateLabel.setText(travelDate.toString());
            fareLabel.setText("₹500"); // can be dynamic later

        } catch (Exception e) {
            e.printStackTrace();
            showError("Booking failed. Please try again.");
        }
    }

    // ===== CHECK SEAT AVAILABILITY =====
    private boolean isSeatAvailable(Connection conn) throws Exception {

        String sql = """
            SELECT is_booked FROM seats
            WHERE bus_id = ? AND seat_number = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setInt(2, seatNumber);

            ResultSet rs = ps.executeQuery();
            return rs.next() && !rs.getBoolean("is_booked");
        }
    }

    // ===== INSERT BOOKING =====
    private int insertBooking(Connection conn) throws Exception {

        String sql = """
            INSERT INTO bookings (user_id, bus_id, seat_number, booking_date)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps =
                     conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, busId);
            ps.setInt(3, seatNumber);
            ps.setDate(4, java.sql.Date.valueOf(travelDate));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        throw new Exception("Booking ID generation failed");
    }

    // ===== MARK SEAT AS BOOKED =====
    private void markSeatBooked(Connection conn) throws Exception {

        String sql = """
            UPDATE seats
            SET is_booked = true
            WHERE bus_id = ? AND seat_number = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setInt(2, seatNumber);
            ps.executeUpdate();
        }
    }

    // ===== LOAD BUS DETAILS =====
    private void loadBusDetails(Connection conn) throws Exception {

        String sql = """
            SELECT bus_number, source, destination
            FROM buses WHERE bus_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                busNumberLabel.setText(rs.getString("bus_number"));
                routeLabel.setText(
                        rs.getString("source") + " → " +
                        rs.getString("destination")
                );
            }
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
