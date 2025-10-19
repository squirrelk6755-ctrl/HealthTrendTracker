package com.healthtracker.dao;

import com.healthtracker.model.HealthRecord;
import com.healthtracker.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthRecordDAO {

    public boolean save(HealthRecord r) {
        String sql = "INSERT INTO health_records(user_id, record_date, weight_kg, height_cm, bmi, sleep_hours, water_liters, calories_intake, mood, notes) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getRecordDate());
            ps.setObject(3, r.getWeightKg());
            ps.setObject(4, r.getHeightCm());
            ps.setObject(5, r.getBmi());
            ps.setObject(6, r.getSleepHours());
            ps.setObject(7, r.getWaterLiters());
            ps.setObject(8, r.getCaloriesIntake());
            ps.setString(9, r.getMood());
            ps.setString(10, r.getNotes());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error saving health record: " + e.getMessage());
            return false;
        }
    }

    public List<HealthRecord> findByUser(int userId) {
        String sql = "SELECT * FROM health_records WHERE user_id = ? ORDER BY record_date ASC";
        List<HealthRecord> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HealthRecord r = new HealthRecord();
                    r.setId(rs.getInt("id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setRecordDate(rs.getString("record_date"));
                    r.setWeightKg((Double) rs.getObject("weight_kg"));
                    r.setHeightCm((Double) rs.getObject("height_cm"));
                    r.setBmi((Double) rs.getObject("bmi"));
                    r.setSleepHours((Double) rs.getObject("sleep_hours"));
                    r.setWaterLiters((Double) rs.getObject("water_liters"));
                    r.setCaloriesIntake((Integer) rs.getObject("calories_intake"));
                    r.setMood(rs.getString("mood"));
                    r.setNotes(rs.getString("notes"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
