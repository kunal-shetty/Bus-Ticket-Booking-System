package com.busbooking.controller;

import com.busbooking.dao.BusDAO;
import com.busbooking.model.Bus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SearchBusController {

    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private TableView<Bus> busTable;
    @FXML
    private TableColumn<Bus, Integer> busIdColumn;
    @FXML
    private TableColumn<Bus, String> busNumberColumn;
    @FXML
    private TableColumn<Bus, String> sourceColumn;
    @FXML
    private TableColumn<Bus, String> destinationColumn;
    @FXML
    private TableColumn<Bus, Integer> seatsColumn;
    @FXML
    private Label messageLabel;

    private final BusDAO busDAO = new BusDAO();

    // ===== INITIALIZATION =====
    @FXML
    public void initialize() {
        busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));

        // Load all buses on init
        loadBuses();
    }

    // ===== KEPT FOR BACKWARDS COMPATIBILITY =====
    public void setUserSession(int userId) {
        // No longer needed — SessionService handles this
        // Trigger a refresh in case it was called
        loadBuses();
    }

    // ===== LOAD ALL BUSES =====
    private void loadBuses() {
        List<Bus> buses = busDAO.getAllBuses();
        messageLabel.setText("");
        ObservableList<Bus> busList = FXCollections.observableArrayList(buses);
        busTable.setItems(busList);

        if (buses.isEmpty()) {
            messageLabel.setText("No buses available");
        }
    }

    // ===== SEARCH HANDLER =====
    @FXML
    private void handleSearch() {
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (source.isEmpty() || destination.isEmpty()) {
            showMessage("Please enter source and destination", true);
            busTable.getItems().clear();
            return;
        }

        List<Bus> buses = busDAO.searchBuses(source, destination);

        if (buses.isEmpty()) {
            showMessage("No buses found for this route", true);
            busTable.getItems().clear();
        } else {
            showMessage(buses.size() + " bus(es) found", false);
            ObservableList<Bus> busList = FXCollections.observableArrayList(buses);
            busTable.setItems(busList);
        }
    }

    // ===== SHOW ALL BUSES =====
    @FXML
    private void handleShowAll() {
        sourceField.clear();
        destinationField.clear();
        loadBuses();
    }

    // ===== SELECT BUS HANDLER =====
    @FXML
    private void handleSelectBus() {
        Bus selectedBus = busTable.getSelectionModel().getSelectedItem();

        if (selectedBus == null) {
            showMessage("Please select a bus first", true);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/seat_selection.fxml"));
            Parent root = loader.load();

            SeatSelectionController controller = loader.getController();
            controller.setBookingContext(
                    selectedBus.getBusId(),
                    selectedBus.getBusNumber(),
                    selectedBus.getSource(),
                    selectedBus.getDestination());

            Stage stage = (Stage) busTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error loading seat selection: " + e.getMessage());
            showMessage("Failed to open seat selection", true);
        }
    }

    // ===== BACK HANDLER =====
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) busTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
        }
    }

    // ===== HELPERS =====
    private void showMessage(String text, boolean isError) {
        messageLabel.setText(text);
        messageLabel.getStyleClass().removeAll("error-label", "success-label");
        messageLabel.getStyleClass().add(isError ? "error-label" : "success-label");
    }
}
