package com.healthtracker.model;

public class HealthRecord {
    private int id;
    private int userId;
    private String recordDate; // yyyy-MM-dd
    private Double weightKg;
    private Double heightCm;
    private Double bmi;
    private Double sleepHours;
    private Double waterLiters;
    private Integer caloriesIntake;
    private String mood;
    private String notes;

    public HealthRecord() {}

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getRecordDate() { return recordDate; }
    public void setRecordDate(String recordDate) { this.recordDate = recordDate; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }
    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }
    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }
    public Double getWaterLiters() { return waterLiters; }
    public void setWaterLiters(Double waterLiters) { this.waterLiters = waterLiters; }
    public Integer getCaloriesIntake() { return caloriesIntake; }
    public void setCaloriesIntake(Integer caloriesIntake) { this.caloriesIntake = caloriesIntake; }
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
