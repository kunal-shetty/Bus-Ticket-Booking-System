package com.busbooking.model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int userId;
    private int busId;
    private int seatNumber;
    private LocalDate travelDate;

    public Booking(int bookingId, int userId, int busId,
                   int seatNumber, LocalDate travelDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
    }
}
