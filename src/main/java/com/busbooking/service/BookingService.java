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

            // Create booking
            int bookingId = bookingDAO.createBooking(
                    userId, busId, seatNumber, travelDate
            );

            if (bookingId == -1) {
                conn.rollback();
                return -1;
            }

            // Mark seat booked
            seatDAO.markSeatBooked(busId, seatNumber);

            conn.commit();
            return bookingId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
