package com.busbooking.service;

import com.busbooking.dao.BookingDAO;
import com.busbooking.dao.SeatDAO;
import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.time.LocalDate;

public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    public int bookSeat(int userId,
            int busId,
            int seatNumber,
            LocalDate travelDate) {

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {
                // Create booking
                int bookingId = bookingDAO.createBooking(
                        conn, userId, busId, seatNumber, travelDate);

                // Mark seat booked
                seatDAO.markSeatBooked(conn, busId, seatNumber);

                conn.commit();
                return bookingId;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                throw e; // Rethrow or handle gracefully
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
