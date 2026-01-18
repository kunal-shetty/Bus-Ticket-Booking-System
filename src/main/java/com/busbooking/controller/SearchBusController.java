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
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SearchBusController {

    // ===== FXML FIELDS =====
    @FXML private TextField sourceField;
    @FXML private TextField destinationField;

    @FXML private TableView<Bus> busTable;
    @FXML private TableColumn<Bus, Integer> busIdColumn;
    @FXML private TableColumn<Bus, String> busNumberColumn;
    @FXML private TableColumn<Bus, String> sourceColumn;
    @FXML private TableColumn<Bus, String> destinationColumn;
    @FXML private TableColumn<Bus, Integer> seatsColumn;

    @FXML private Label messageLabel;

    // ===== DAO =====
    private final BusDAO busDAO = new BusDAO();

    // ===== SESSION =====
    private int loggedInUserId;

    // ===== INITIALIZATION =====
    @FXML
    public void initialize() {

        busIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("busId")
        );
        busNumberColumn.setCellValueFactory(
                new PropertyValueFactory<>("busNumber")
        );
        sourceColumn.setCellValueFactory(
                new PropertyValueFactory<>("source")
        );
        destinationColumn.setCellValueFactory(
                new PropertyValueFactory<>("destination")
        );
        seatsColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalSeats")
        );
    }

    // ===== SESSION SETTER =====
    public void setUserSession(int userId) {
        this.loggedInUserId = userId;
    }

    // ===== SEARCH HANDLER =====
    @FXML
    private void handleSearch() {

        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (source.isEmpty() || destination.isEmpty()) {
            messageLabel.setText("Please enter source and destination");
            busTable.getItems().clear();
            return;
        }

        List<Bus> buses = busDAO.searchBuses(source, destination);

        if (buses.isEmpty()) {
            messageLabel.setText("No buses found for this route");
            busTable.getItems().clear();
        } else {
            messageLabel.setText("");
            ObservableList<Bus> busList =
                    FXCollections.observableArrayList(buses);
            busTable.setItems(busList);
        }
    }

    // ===== SELECT BUS HANDLER =====
    @FXML
    private void handleSelectBus() {

        Bus selectedBus = busTable.getSelectionModel().getSelectedItem();

        if (selectedBus == null) {
            messageLabel.setText("Please select a bus first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/seat_selection.fxml")
            );
            Parent root = loader.load();

            SeatSelectionController controller = loader.getController();
            controller.setBookingContext(
                    loggedInUserId,
                    selectedBus.getBusId(),
                    selectedBus.getBusNumber(),
                    selectedBus.getSource(),
                    selectedBus.getDestination()
            );

            Stage stage = (Stage) busTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Failed to open seat selection");
        }
    }

    // ===== BACK HANDLER =====
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUserSession(loggedInUserId, "User");

            Stage stage = (Stage) busTable.getScene().getWindow();
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
