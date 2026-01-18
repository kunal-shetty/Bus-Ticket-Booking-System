package com.busbooking.dao;

import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SeatDAO {

    // ===== GET SEATS FOR BUS =====
    public Map<Integer, Boolean> getSeatsByBus(int busId) {

        Map<Integer, Boolean> seats = new HashMap<>();
        String sql = """
            SELECT seat_number, is_booked
            FROM seats
            WHERE bus_id = ?
            ORDER BY seat_number
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seats.put(
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    // ===== MARK SEAT BOOKED =====
    public void markSeatBooked(int busId, int seatNumber) {

        String sql = """
            UPDATE seats
            SET is_booked = true
            WHERE bus_id = ? AND seat_number = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ps.setInt(2, seatNumber);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
