package com.busbooking.dao;

import com.busbooking.model.Booking;
import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // ===== CREATE BOOKING (Transactional) =====
    public int createBooking(Connection conn, int userId, int busId,
            int seatNumber, LocalDate travelDate) throws SQLException {

        String sql = "INSERT INTO bookings (user_id, bus_id, seat_number, booking_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, busId);
            ps.setInt(3, seatNumber);
            ps.setDate(4, java.sql.Date.valueOf(travelDate));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        }
    }

    // ===== GET BOOKINGS BY USER =====
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT b.booking_id, b.user_id, b.bus_id, b.seat_number, b.booking_date,
                       bu.bus_number, bu.source, bu.destination
                FROM bookings b
                JOIN buses bu ON b.bus_id = bu.bus_id
                WHERE b.user_id = ?
                ORDER BY b.booking_date DESC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setBusId(rs.getInt("bus_id"));
                    booking.setSeatNumber(rs.getInt("seat_number"));
                    booking.setBookingDate(rs.getDate("booking_date").toLocalDate());
                    booking.setBusNumber(rs.getString("bus_number"));
                    booking.setSource(rs.getString("source"));
                    booking.setDestination(rs.getString("destination"));

                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
        }
        return bookings;
    }
}
