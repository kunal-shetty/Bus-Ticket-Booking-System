package com.busbooking.service;

import com.busbooking.dao.BookingDAO;
import com.busbooking.dao.SeatDAO;
import com.busbooking.exception.BookingException;
import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    // ===== SINGLE SEAT (backwards compatible) =====
    public int bookSeat(int userId, int busId, int seatNumber,
            LocalDate travelDate) throws BookingException {
        List<Integer> result = bookSeats(userId, busId, List.of(seatNumber), travelDate);
        return result.get(0);
    }

    // ===== MULTIPLE SEATS (new) =====
    public List<Integer> bookSeats(int userId, int busId, List<Integer> seatNumbers,
            LocalDate travelDate) throws BookingException {

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Check all seats are available first
            for (int seatNumber : seatNumbers) {
                if (!seatDAO.isSeatAvailable(busId, seatNumber)) {
                    throw new BookingException("Seat " + seatNumber + " is already booked.");
                }
            }

            // Book all seats in one transaction
            List<Integer> bookingIds = new ArrayList<>();
            for (int seatNumber : seatNumbers) {
                int bookingId = bookingDAO.createBooking(conn, userId, busId, seatNumber, travelDate);
                seatDAO.markSeatBooked(conn, busId, seatNumber);
                bookingIds.add(bookingId);
            }

            conn.commit();
            return bookingIds;

        } catch (BookingException e) {
            rollbackQuietly(conn);
            throw e;
        } catch (SQLException e) {
            rollbackQuietly(conn);
            throw new BookingException("Booking failed: " + e.getMessage(), e);
        } finally {
            closeQuietly(conn);
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
