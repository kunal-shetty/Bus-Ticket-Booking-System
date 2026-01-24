package com.busbooking.dao;

import com.busbooking.model.Bus;
import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    // ===== GET ALL BUSES =====
    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                buses.add(mapBus(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    // ===== SEARCH BUSES =====
    public List<Bus> searchBuses(String source, String destination) {

        List<Bus> buses = new ArrayList<>();
        String sql = """
                    SELECT * FROM buses
                    WHERE LOWER(source) = LOWER(?)
                    AND LOWER(destination) = LOWER(?)
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                buses.add(mapBus(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    // ===== HELPER =====
    private Bus mapBus(ResultSet rs) throws SQLException {
        return new Bus(
                rs.getInt("bus_id"),
                rs.getString("bus_number"),
                rs.getString("source"),
                rs.getString("destination"),
                rs.getInt("total_seats"));
    }
}
