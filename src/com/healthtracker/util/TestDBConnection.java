package com.healthtracker.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBConnection {

    public static void main(String[] args) {
        // Step 1: Get a connection from DBConnection.java
        try (Connection conn = DBConnection.getConnection()) {

            if (conn != null) {
                System.out.println("✅ Connected to SQLite database successfully!");

                // Step 2: Display database info
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver Name: " + meta.getDriverName());
                System.out.println("Database Product: " + meta.getDatabaseProductName());
                System.out.println("Database Version: " + meta.getDatabaseProductVersion());

                // Step 3: Optional — create a small test table
                Statement stmt = conn.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS test_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
                stmt.execute(createTable);
                System.out.println("🧱 Test table created (if not exists).");

                // Step 4: Insert sample data
                stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('HealthTracker Demo')");
                System.out.println("💾 Sample data inserted.");

                // Step 5: Retrieve and show data
                ResultSet rs = stmt.executeQuery("SELECT * FROM test_table");
                System.out.println("📊 Records in test_table:");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name"));
                }

                // Step 6: Close resources automatically via try-with-resources
                System.out.println("✅ Database connectivity test successful.");

            } else {
                System.out.println("❌ Connection failed! Check your DBConnection.java setup.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }
}
