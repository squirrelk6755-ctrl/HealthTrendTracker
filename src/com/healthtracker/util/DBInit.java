package com.healthtracker.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DBInit {
    public static void createDatabaseAndTables() {
        try {
            // ensure folder exists
            File dbDir = new File("database");
            if (!dbDir.exists()) dbDir.mkdirs();

            try (Connection c = DBConnection.getConnection();
                 Statement s = c.createStatement()) {
                // users
                s.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "username TEXT UNIQUE NOT NULL,"
                        + "password_hash TEXT NOT NULL,"
                        + "full_name TEXT,"
                        + "dob TEXT,"
                        + "gender TEXT,"
                        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                        + ");");

                // health_records
                s.execute("CREATE TABLE IF NOT EXISTS health_records ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "user_id INTEGER NOT NULL,"
                        + "record_date TEXT NOT NULL,"
                        + "weight_kg REAL,"
                        + "height_cm REAL,"
                        + "bmi REAL,"
                        + "sleep_hours REAL,"
                        + "water_liters REAL,"
                        + "calories_intake INTEGER,"
                        + "mood TEXT,"
                        + "notes TEXT,"
                        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "FOREIGN KEY(user_id) REFERENCES users(id)"
                        + ");");

                // badges
                s.execute("CREATE TABLE IF NOT EXISTS badges ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "user_id INTEGER NOT NULL,"
                        + "name TEXT,"
                        + "earned_on TEXT,"
                        + "FOREIGN KEY(user_id) REFERENCES users(id)"
                        + ");");
            }
        } catch (SQLException e) {
            System.err.println("Failed to initialize database/tables:");
            e.printStackTrace();
        }
    }
}
