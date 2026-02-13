# 🚌 Bus Ticket Booking System (JavaFX + MySQL)

A desktop Bus Ticket Booking System built using **Java**, **JavaFX**, **JDBC**, and **MySQL**.
This project demonstrates GUI-based development using the MVC pattern, FXML views, and database connectivity.

---

## ⚙️ Setup & Run Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Bus-Ticket-Booking-System.git
cd Bus-Ticket-Booking-System
```

### 2. Install & Start MySQL

1. Download and install **WAMP**, **XAMPP**, or a standalone MySQL server
2. Open the control panel
3. Start **MySQL** service

> MySQL must be running before continuing.

### 3. Create Database & Tables

#### Using phpMyAdmin

1. Open **phpMyAdmin** from your control panel
2. Go to the **Import** tab
3. Select the `databaseTables.sql` file and click **Go**

This will create the `bus_ticket_booking_db` database and all required tables.

### 4. (Optional) Load Sample Data

Import `seed_data.sql` via phpMyAdmin to populate the database with sample buses, seats, and users.

### 5. Configure Database Credentials

Edit `src/main/java/com/busbooking/util/DBConnection.java` and update:
- `USER` — your MySQL username (default: `root`)
- `PASSWORD` — your MySQL password

### 6. Compile & Run

```powershell
.\compile.ps1
```

This script will:
- Compile all Java source files with JavaFX and MySQL connector
- Copy resources (FXML, CSS, images) to the output directory
- Launch the application

---

## 📌 Notes

- Default MySQL port: **3306**
- Update the JDBC URL in `DBConnection.java` if using a different port
- JavaFX SDK is bundled in the `lib/javafx/` directory
