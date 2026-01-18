package com.busbooking.service;

import com.busbooking.dao.SeatDAO;

import java.util.Map;

public class SeatService {

    private final SeatDAO seatDAO = new SeatDAO();

    public Map<Integer, Boolean> getSeatAvailability(int busId) {
        return seatDAO.getSeatsByBus(busId);
    }

    public void markSeatBooked(int busId, int seatNumber) {
        seatDAO.markSeatBooked(busId, seatNumber);
    }
}
