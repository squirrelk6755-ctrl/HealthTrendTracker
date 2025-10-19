package com.healthtracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database file path (relative to your project folder)
    private static final String DB_URL = "jdbc:sqlite:database/healthtracker.db";

    // This method gives you a live connection to the database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Step 1: Load the SQLite JDBC driver (automatically included with the .jar)
            Class.forName("org.sqlite.JDBC");

            // Step 2: Connect to the database (creates file if it doesn’t exist)
            conn = DriverManager.getConnection(DB_URL);

        } catch (ClassNotFoundException e) {
            System.out.println("❌ SQLite JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error connecting to database: " + e.getMessage());
        }
        return conn;
    }

    // Optional: test method (for beginners)
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connection successful!");
            } else {
                System.out.println("❌ Connection failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
