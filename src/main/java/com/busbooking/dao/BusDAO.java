package com.busbooking.dao;

import com.busbooking.model.Bus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    private static final String URL =
            "jdbc:mysql://localhost:3306/bus_ticket_booking_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kunal1234";

    // 🔹 Fetch all buses from DB
    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();

        String sql = "SELECT * FROM buses";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_number"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats")
                );
                buses.add(bus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buses;
    }
}
