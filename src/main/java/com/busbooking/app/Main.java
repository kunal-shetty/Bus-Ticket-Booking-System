package com.busbooking.app;

import com.busbooking.dao.BusDAO;
import com.busbooking.model.Bus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String URL =
            "jdbc:mysql://localhost:3306/bus_ticket_booking_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kunal1234";

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   BUS TICKET BOOKING SYSTEM");
        System.out.println("=================================");

        if (!testDatabaseConnection()) {
            System.out.println("❌ Cannot start application without database");
            return;
        }

        Scanner sc = new Scanner(System.in);
        BusDAO busDAO = new BusDAO();
        int choice;

        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View All Buses");
            System.out.println("2. Book Ticket");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewAllBuses(busDAO);
                    break;

                case 2:
                    System.out.println("👉 Booking logic coming next");
                    break;

                case 3:
                    System.out.println("👋 Thank you for using the system");
                    break;

                default:
                    System.out.println("❌ Invalid choice");
            }

        } while (choice != 3);

        sc.close();
    }

    // 🔹 Show buses in console
    private static void viewAllBuses(BusDAO busDAO) {

        List<Bus> buses = busDAO.getAllBuses();

        if (buses.isEmpty()) {
            System.out.println("❌ No buses available");
            return;
        }

        System.out.println("\n--- AVAILABLE BUSES ---");
        System.out.printf("%-5s %-12s %-15s %-15s %-10s%n",
                "ID", "Bus No", "Source", "Destination", "Seats");
        System.out.println("---------------------------------------------------------");

        for (Bus bus : buses) {
            System.out.printf("%-5d %-12s %-15s %-15s %-10d%n",
                    bus.getBusId(),
                    bus.getBusNumber(),
                    bus.getSource(),
                    bus.getDestination(),
                    bus.getTotalSeats()
            );
        }
    }

    // 🔹 DB connection test (once at startup)
    private static boolean testDatabaseConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Database connected successfully");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Database connection failed");
            e.printStackTrace();
            return false;
        }
    }
}
