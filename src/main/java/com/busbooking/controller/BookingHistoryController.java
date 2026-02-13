package com.busbooking.controller;

import com.busbooking.dao.BookingDAO;
import com.busbooking.model.Booking;
import com.busbooking.service.SessionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class BookingHistoryController {

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, Integer> bookingIdColumn;
    @FXML
    private TableColumn<Booking, String> busNumberColumn;
    @FXML
    private TableColumn<Booking, String> sourceColumn;
    @FXML
    private TableColumn<Booking, String> destinationColumn;
    @FXML
    private TableColumn<Booking, Integer> seatNumberColumn;
    @FXML
    private TableColumn<Booking, LocalDate> dateColumn;
    @FXML
    private Label emptyStateLabel;

    private final BookingDAO bookingDAO = new BookingDAO();

    @FXML
    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        loadBookings();
    }

    // ===== KEPT FOR BACKWARDS COMPATIBILITY =====
    public void setUserSession(int userId) {
        loadBookings();
    }

    private void loadBookings() {
        int userId = SessionService.getUserId();
        List<Booking> bookings = bookingDAO.getBookingsByUserId(userId);
        ObservableList<Booking> bookingList = FXCollections.observableArrayList(bookings);
        bookingTable.setItems(bookingList);

        // Show/hide empty state
        if (emptyStateLabel != null) {
            emptyStateLabel.setVisible(bookings.isEmpty());
            emptyStateLabel.setManaged(bookings.isEmpty());
        }
        bookingTable.setVisible(!bookings.isEmpty());
        bookingTable.setManaged(!bookings.isEmpty());
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) bookingTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
        }
    }
}
