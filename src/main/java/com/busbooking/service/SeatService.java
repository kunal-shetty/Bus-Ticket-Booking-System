package com.busbooking.service;

import com.busbooking.dao.SeatDAO;
import com.busbooking.util.DBConnection;

import java.sql.Connection;
import java.util.Map;

public class SeatService {

    private final SeatDAO seatDAO = new SeatDAO();

    public Map<Integer, Boolean> getSeatAvailability(int busId) {
        return seatDAO.getSeatsByBus(busId);
    }

    public void markSeatBooked(int busId, int seatNumber) {
        try (Connection conn = DBConnection.getConnection()) {
            seatDAO.markSeatBooked(conn, busId, seatNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
