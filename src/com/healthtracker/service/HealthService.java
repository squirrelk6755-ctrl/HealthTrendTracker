package com.healthtracker.service;

import com.healthtracker.model.HealthRecord;

public class HealthService {

    public void computeAndSetBMI(HealthRecord r) {
        if (r.getHeightCm() != null && r.getWeightKg() != null && r.getHeightCm() > 0) {
            double meters = r.getHeightCm() / 100.0;
            double bmi = r.getWeightKg() / (meters * meters);
            r.setBmi(Math.round(bmi * 100.0) / 100.0);
        }
    }

    public String whoBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }
}
