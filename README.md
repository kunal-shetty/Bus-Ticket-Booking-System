
````md
# 🚌 Bus Ticket Booking System (Java + MySQL)

A console-based Bus Ticket Booking System built using **Java**, **JDBC**, and **MySQL**.  
This project demonstrates backend development using the DAO pattern and database connectivity.

---

## ⚙️ Setup & Run Instructions (Follow in Order)

---

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/Bus-Ticket-Booking-System.git
cd Bus-Ticket-Booking-System
````

---

## 2️⃣ Install & Start WAMP or XAMPP

1. Download and install the server
2. Open **The Control Panel**
3. Start **MySQL**

> MySQL must be running before continuing.

---

## 3️⃣ Create Database & Tables (Using SQL File)

### Using phpMyAdmin 

1. In the Control Panel, open **phpMyAdmin**
2. Go to the **Import** tab
3. Select the `databaseTables.sql` file
4. Click **Go**

This will automatically create the database and required tables.


## 5️⃣ Compile & Run the Application (Recommended)

Run the provided script:

```powershell
.\compile.ps1
```

This script will:

* Compile all Java source files
* Run the application automatically

Run:

```powershell
java -cp "lib/mysql-connector-j.jar;bin" com.busbooking.app.Main
```
---

## 📌 Notes

* MySQL credentials assumed:

  * **Username:** root
  * **Password:** (According to your config)
* Default MySQL port: **3306**
* Update JDBC URL in code if using a different port

