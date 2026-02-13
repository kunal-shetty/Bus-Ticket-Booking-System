USE bus_ticket_booking_db;

-- =============================================
-- SEED DATA: Indian Bus Routes
-- =============================================

-- 1. Users
INSERT INTO users (name, email, password) VALUES
('Kunal Shetty', 'kunal@gmail.com', '123456'),
('Mihir Shetty', 'mihir@gmail.com', 'password'),
('Test User', 'test@gmail.com', '12345678'),
('Aarav Sharma', 'aarav@gmail.com', 'aarav123'),
('Priya Patel', 'priya@gmail.com', 'priya456'),
('Rohan Desai', 'rohan@gmail.com', 'rohan789');

-- 2. Buses (Indian Routes)

-- Maharashtra Routes
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('MH-12-AB-1234', 'Mumbai', 'Pune', 20);
SET @bus1 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('MH-14-XY-9876', 'Pune', 'Mumbai', 20);
SET @bus2 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('MH-04-CD-5678', 'Mumbai', 'Nashik', 20);
SET @bus3 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('MH-31-EF-3456', 'Nagpur', 'Pune', 20);
SET @bus4 = LAST_INSERT_ID();

-- Karnataka Routes
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('KA-01-ZZ-5555', 'Bangalore', 'Chennai', 20);
SET @bus5 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('KA-05-MN-7777', 'Bangalore', 'Mysore', 20);
SET @bus6 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('KA-19-PQ-8888', 'Bangalore', 'Goa', 20);
SET @bus7 = LAST_INSERT_ID();

-- North India Routes
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('DL-01-AA-1111', 'Delhi', 'Agra', 20);
SET @bus8 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('DL-08-BB-2222', 'Delhi', 'Jaipur', 20);
SET @bus9 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('UP-32-CC-3333', 'Lucknow', 'Varanasi', 20);
SET @bus10 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('RJ-14-DD-4444', 'Jaipur', 'Udaipur', 20);
SET @bus11 = LAST_INSERT_ID();

-- South India Routes
INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('TN-01-EE-6666', 'Chennai', 'Madurai', 20);
SET @bus12 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('KL-01-FF-9999', 'Kochi', 'Trivandrum', 20);
SET @bus13 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('AP-28-GG-1010', 'Hyderabad', 'Vijayawada', 20);
SET @bus14 = LAST_INSERT_ID();

INSERT INTO buses (bus_number, source, destination, total_seats) VALUES
('TN-09-HH-2020', 'Coimbatore', 'Bangalore', 20);
SET @bus15 = LAST_INSERT_ID();

-- 3. Seats (20 per bus)

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus1,1,0),(@bus1,2,0),(@bus1,3,0),(@bus1,4,0),(@bus1,5,0),(@bus1,6,0),(@bus1,7,0),(@bus1,8,0),(@bus1,9,0),(@bus1,10,0),
(@bus1,11,0),(@bus1,12,0),(@bus1,13,0),(@bus1,14,0),(@bus1,15,0),(@bus1,16,0),(@bus1,17,0),(@bus1,18,0),(@bus1,19,0),(@bus1,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus2,1,0),(@bus2,2,0),(@bus2,3,0),(@bus2,4,0),(@bus2,5,0),(@bus2,6,0),(@bus2,7,0),(@bus2,8,0),(@bus2,9,0),(@bus2,10,0),
(@bus2,11,0),(@bus2,12,0),(@bus2,13,0),(@bus2,14,0),(@bus2,15,0),(@bus2,16,0),(@bus2,17,0),(@bus2,18,0),(@bus2,19,0),(@bus2,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus3,1,0),(@bus3,2,0),(@bus3,3,0),(@bus3,4,0),(@bus3,5,0),(@bus3,6,0),(@bus3,7,0),(@bus3,8,0),(@bus3,9,0),(@bus3,10,0),
(@bus3,11,0),(@bus3,12,0),(@bus3,13,0),(@bus3,14,0),(@bus3,15,0),(@bus3,16,0),(@bus3,17,0),(@bus3,18,0),(@bus3,19,0),(@bus3,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus4,1,0),(@bus4,2,0),(@bus4,3,0),(@bus4,4,0),(@bus4,5,0),(@bus4,6,0),(@bus4,7,0),(@bus4,8,0),(@bus4,9,0),(@bus4,10,0),
(@bus4,11,0),(@bus4,12,0),(@bus4,13,0),(@bus4,14,0),(@bus4,15,0),(@bus4,16,0),(@bus4,17,0),(@bus4,18,0),(@bus4,19,0),(@bus4,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus5,1,0),(@bus5,2,0),(@bus5,3,0),(@bus5,4,0),(@bus5,5,0),(@bus5,6,0),(@bus5,7,0),(@bus5,8,0),(@bus5,9,0),(@bus5,10,0),
(@bus5,11,0),(@bus5,12,0),(@bus5,13,0),(@bus5,14,0),(@bus5,15,0),(@bus5,16,0),(@bus5,17,0),(@bus5,18,0),(@bus5,19,0),(@bus5,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus6,1,0),(@bus6,2,0),(@bus6,3,0),(@bus6,4,0),(@bus6,5,0),(@bus6,6,0),(@bus6,7,0),(@bus6,8,0),(@bus6,9,0),(@bus6,10,0),
(@bus6,11,0),(@bus6,12,0),(@bus6,13,0),(@bus6,14,0),(@bus6,15,0),(@bus6,16,0),(@bus6,17,0),(@bus6,18,0),(@bus6,19,0),(@bus6,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus7,1,0),(@bus7,2,0),(@bus7,3,0),(@bus7,4,0),(@bus7,5,0),(@bus7,6,0),(@bus7,7,0),(@bus7,8,0),(@bus7,9,0),(@bus7,10,0),
(@bus7,11,0),(@bus7,12,0),(@bus7,13,0),(@bus7,14,0),(@bus7,15,0),(@bus7,16,0),(@bus7,17,0),(@bus7,18,0),(@bus7,19,0),(@bus7,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus8,1,0),(@bus8,2,0),(@bus8,3,0),(@bus8,4,0),(@bus8,5,0),(@bus8,6,0),(@bus8,7,0),(@bus8,8,0),(@bus8,9,0),(@bus8,10,0),
(@bus8,11,0),(@bus8,12,0),(@bus8,13,0),(@bus8,14,0),(@bus8,15,0),(@bus8,16,0),(@bus8,17,0),(@bus8,18,0),(@bus8,19,0),(@bus8,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus9,1,0),(@bus9,2,0),(@bus9,3,0),(@bus9,4,0),(@bus9,5,0),(@bus9,6,0),(@bus9,7,0),(@bus9,8,0),(@bus9,9,0),(@bus9,10,0),
(@bus9,11,0),(@bus9,12,0),(@bus9,13,0),(@bus9,14,0),(@bus9,15,0),(@bus9,16,0),(@bus9,17,0),(@bus9,18,0),(@bus9,19,0),(@bus9,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus10,1,0),(@bus10,2,0),(@bus10,3,0),(@bus10,4,0),(@bus10,5,0),(@bus10,6,0),(@bus10,7,0),(@bus10,8,0),(@bus10,9,0),(@bus10,10,0),
(@bus10,11,0),(@bus10,12,0),(@bus10,13,0),(@bus10,14,0),(@bus10,15,0),(@bus10,16,0),(@bus10,17,0),(@bus10,18,0),(@bus10,19,0),(@bus10,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus11,1,0),(@bus11,2,0),(@bus11,3,0),(@bus11,4,0),(@bus11,5,0),(@bus11,6,0),(@bus11,7,0),(@bus11,8,0),(@bus11,9,0),(@bus11,10,0),
(@bus11,11,0),(@bus11,12,0),(@bus11,13,0),(@bus11,14,0),(@bus11,15,0),(@bus11,16,0),(@bus11,17,0),(@bus11,18,0),(@bus11,19,0),(@bus11,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus12,1,0),(@bus12,2,0),(@bus12,3,0),(@bus12,4,0),(@bus12,5,0),(@bus12,6,0),(@bus12,7,0),(@bus12,8,0),(@bus12,9,0),(@bus12,10,0),
(@bus12,11,0),(@bus12,12,0),(@bus12,13,0),(@bus12,14,0),(@bus12,15,0),(@bus12,16,0),(@bus12,17,0),(@bus12,18,0),(@bus12,19,0),(@bus12,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus13,1,0),(@bus13,2,0),(@bus13,3,0),(@bus13,4,0),(@bus13,5,0),(@bus13,6,0),(@bus13,7,0),(@bus13,8,0),(@bus13,9,0),(@bus13,10,0),
(@bus13,11,0),(@bus13,12,0),(@bus13,13,0),(@bus13,14,0),(@bus13,15,0),(@bus13,16,0),(@bus13,17,0),(@bus13,18,0),(@bus13,19,0),(@bus13,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus14,1,0),(@bus14,2,0),(@bus14,3,0),(@bus14,4,0),(@bus14,5,0),(@bus14,6,0),(@bus14,7,0),(@bus14,8,0),(@bus14,9,0),(@bus14,10,0),
(@bus14,11,0),(@bus14,12,0),(@bus14,13,0),(@bus14,14,0),(@bus14,15,0),(@bus14,16,0),(@bus14,17,0),(@bus14,18,0),(@bus14,19,0),(@bus14,20,0);

INSERT INTO seats (bus_id, seat_number, is_booked) VALUES
(@bus15,1,0),(@bus15,2,0),(@bus15,3,0),(@bus15,4,0),(@bus15,5,0),(@bus15,6,0),(@bus15,7,0),(@bus15,8,0),(@bus15,9,0),(@bus15,10,0),
(@bus15,11,0),(@bus15,12,0),(@bus15,13,0),(@bus15,14,0),(@bus15,15,0),(@bus15,16,0),(@bus15,17,0),(@bus15,18,0),(@bus15,19,0),(@bus15,20,0);

-- 4. Sample Bookings (pre-book some seats to show mixed state)
-- Mark some seats as booked for realistic demo
UPDATE seats SET is_booked = 1 WHERE bus_id = @bus1 AND seat_number IN (3, 7, 12, 15);
UPDATE seats SET is_booked = 1 WHERE bus_id = @bus5 AND seat_number IN (1, 5, 9, 14, 20);
UPDATE seats SET is_booked = 1 WHERE bus_id = @bus8 AND seat_number IN (2, 6, 10);

INSERT INTO bookings (user_id, bus_id, seat_number, booking_date) VALUES
((SELECT user_id FROM users WHERE email='kunal@gmail.com'), @bus1, 3, '2026-02-10'),
((SELECT user_id FROM users WHERE email='kunal@gmail.com'), @bus1, 7, '2026-02-10'),
((SELECT user_id FROM users WHERE email='mihir@gmail.com'), @bus1, 12, '2026-02-11'),
((SELECT user_id FROM users WHERE email='priya@gmail.com'), @bus1, 15, '2026-02-12'),
((SELECT user_id FROM users WHERE email='aarav@gmail.com'), @bus5, 1, '2026-02-09'),
((SELECT user_id FROM users WHERE email='aarav@gmail.com'), @bus5, 5, '2026-02-09'),
((SELECT user_id FROM users WHERE email='rohan@gmail.com'), @bus8, 2, '2026-02-13');
