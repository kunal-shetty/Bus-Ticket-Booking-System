USE bus_ticket_booking_db;

-- 1. Clean up existing data (Optional, be careful!)
-- DELETE FROM bookings;
-- DELETE FROM seats;
-- DELETE FROM buses;
-- DELETE FROM users;

-- 2. Insert Users
INSERT INTO users (name, email, password) VALUES 
('Kunal Shetty', 'kunal@gmail.com', '123456'),
('Mihir Shetty', 'mihir@gmail.com', 'password'),
('Test User', 'test@gmail.com', '12345678');

-- 3. Insert Buses
-- Bus 1: Mumbai -> Pune
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES 
('MH-12-AB-1234', 'Mumbai', 'Pune', 20);
SET @bus1 = LAST_INSERT_ID();

-- Bus 2: Pune -> Mumbai
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES 
('MH-14-XY-9876', 'Pune', 'Mumbai', 20);
SET @bus2 = LAST_INSERT_ID();

-- Bus 3: Bangalore -> Chennai
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES 
('KA-01-ZZ-5555', 'Bangalore', 'Chennai', 20);
SET @bus3 = LAST_INSERT_ID();

-- Bus 4: Delhi -> Agra
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES 
('DL-01-AA-1111', 'Delhi', 'Agra', 20);
SET @bus4 = LAST_INSERT_ID();

-- 4. Insert Seats (Procedure logic simulated with individual inserts for compatibility)

-- Seats for Bus 1
INSERT INTO seats (bus_id, seat_number, is_booked) VALUES 
(@bus1, 1, 0), (@bus1, 2, 0), (@bus1, 3, 0), (@bus1, 4, 0), (@bus1, 5, 0),
(@bus1, 6, 0), (@bus1, 7, 0), (@bus1, 8, 0), (@bus1, 9, 0), (@bus1, 10, 0),
(@bus1, 11, 0), (@bus1, 12, 0), (@bus1, 13, 0), (@bus1, 14, 0), (@bus1, 15, 0),
(@bus1, 16, 0), (@bus1, 17, 0), (@bus1, 18, 0), (@bus1, 19, 0), (@bus1, 20, 0);

-- Seats for Bus 2
INSERT INTO seats (bus_id, seat_number, is_booked) VALUES 
(@bus2, 1, 0), (@bus2, 2, 0), (@bus2, 3, 0), (@bus2, 4, 0), (@bus2, 5, 0),
(@bus2, 6, 0), (@bus2, 7, 0), (@bus2, 8, 0), (@bus2, 9, 0), (@bus2, 10, 0),
(@bus2, 11, 0), (@bus2, 12, 0), (@bus2, 13, 0), (@bus2, 14, 0), (@bus2, 15, 0),
(@bus2, 16, 0), (@bus2, 17, 0), (@bus2, 18, 0), (@bus2, 19, 0), (@bus2, 20, 0);

-- Seats for Bus 3
INSERT INTO seats (bus_id, seat_number, is_booked) VALUES 
(@bus3, 1, 0), (@bus3, 2, 0), (@bus3, 3, 0), (@bus3, 4, 0), (@bus3, 5, 0),
(@bus3, 6, 0), (@bus3, 7, 0), (@bus3, 8, 0), (@bus3, 9, 0), (@bus3, 10, 0),
(@bus3, 11, 0), (@bus3, 12, 0), (@bus3, 13, 0), (@bus3, 14, 0), (@bus3, 15, 0),
(@bus3, 16, 0), (@bus3, 17, 0), (@bus3, 18, 0), (@bus3, 19, 0), (@bus3, 20, 0);

-- Seats for Bus 4
INSERT INTO seats (bus_id, seat_number, is_booked) VALUES 
(@bus4, 1, 0), (@bus4, 2, 0), (@bus4, 3, 0), (@bus4, 4, 0), (@bus4, 5, 0),
(@bus4, 6, 0), (@bus4, 7, 0), (@bus4, 8, 0), (@bus4, 9, 0), (@bus4, 10, 0),
(@bus4, 11, 0), (@bus4, 12, 0), (@bus4, 13, 0), (@bus4, 14, 0), (@bus4, 15, 0),
(@bus4, 16, 0), (@bus4, 17, 0), (@bus4, 18, 0), (@bus4, 19, 0), (@bus4, 20, 0);
