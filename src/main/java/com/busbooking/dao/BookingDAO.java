package com.busbooking.dao;

import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class BookingDAO {

    // ===== CREATE BOOKING =====
    public int createBooking(int userId,
                             int busId,
                             int seatNumber,
                             LocalDate travelDate) {

        String sql = """
            INSERT INTO bookings (user_id, bus_id, seat_number, booking_date)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
