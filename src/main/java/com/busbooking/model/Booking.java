package com.busbooking.model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int userId;
    private int busId;
    private int seatNumber;
    private LocalDate bookingDate;

    // Extra fields for display
    private String busNumber;
    private String source;
    private String destination;

    public Booking() {
    }

    public Booking(int bookingId, int userId, int busId, int seatNumber, LocalDate bookingDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
