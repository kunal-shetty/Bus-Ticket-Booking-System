package com.busbooking.model;

public class Seat {

    private int seatNumber;
    private boolean booked;

    public Seat(int seatNumber, boolean booked) {
        this.seatNumber = seatNumber;
        this.booked = booked;
    }

    public int getSeatNumber() { return seatNumber; }
    public boolean isBooked() { return booked; }
}
