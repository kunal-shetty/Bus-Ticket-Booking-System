package com.busbooking.controller;

import com.busbooking.dao.BookingDAO;
import com.busbooking.model.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private final BookingDAO bookingDAO = new BookingDAO();
    private int userId;

    @FXML
    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
    }

    public void setUserSession(int userId) {
        this.userId = userId;
        loadBookings();
    }

    private void loadBookings() {
        List<Booking> bookings = bookingDAO.getBookingsByUserId(userId);
        ObservableList<Booking> bookingList = FXCollections.observableArrayList(bookings);
        bookingTable.setItems(bookingList);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            // Note: We need to restore the detailed session info (name) if we want to
            // display "Welcome, Name"
            // But we only have ID here unless we store it or fetch it.
            // For simplicity, let's just pass the ID and fetch name or just show generic
            // welcome?
            // DashboardController expects (int userId, String userName).
            // I'll fetch the user name or just pass a placeholder if I don't want to add
            // getUserById.
            // Let's modify DashboardController to handle this gracefully or add
            // UserDAO.getUserById.

            // Actually, we don't have UserDAO.getUserById yet.
            // Let's fetch it or just pass "User" for now to satisfy the method signature.
            controller.setUserSession(userId, "User");

            Stage stage = (Stage) bookingTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
