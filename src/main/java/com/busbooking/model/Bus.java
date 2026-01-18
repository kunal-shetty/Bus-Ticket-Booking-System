package com.busbooking.model;

public class Bus {

    private int busId;
    private String busNumber;
    private String source;
    private String destination;
    private int totalSeats;

    public Bus(int busId, String busNumber, String source,
               String destination, int totalSeats) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    public int getBusId() {
        return busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}
