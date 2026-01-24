package com.busbooking.dao;

import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        rs.getBoolean("is_booked"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    // ===== CHECK IF SEAT IS AVAILABLE =====
    public boolean isSeatAvailable(int busId, int seatNumber) {
        String sql = "SELECT is_booked FROM seats WHERE bus_id = ? AND seat_number = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ps.setInt(2, seatNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return !rs.getBoolean("is_booked");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if error or seat not found (safer)
    }

    // ===== MARK SEAT BOOKED =====
    // Note: Transaction management should ideally happen in a Service layer
    // but for this simple app, we can keep it here or manage connection outside.
    public void markSeatBooked(Connection conn, int busId, int seatNumber) throws SQLException {
        String sql = "UPDATE seats SET is_booked = true WHERE bus_id = ? AND seat_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setInt(2, seatNumber);
            ps.executeUpdate();
        }
    }
}
