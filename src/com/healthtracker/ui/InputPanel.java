package com.healthtracker.ui;

import com.healthtracker.dao.HealthRecordDAO;
import com.healthtracker.dao.UserDAO;
import com.healthtracker.model.HealthRecord;
import com.healthtracker.model.User;
import com.healthtracker.service.HealthService;
import com.healthtracker.util.SimpleHash;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InputPanel extends JPanel {
    public InputPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("✍️ Add New Health Record", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(46, 139, 87));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;

        // Fields
        JLabel lblUser = new JLabel("Username:");
        JTextField tfUser = new JTextField(12);
        JLabel lblDate = new JLabel("Date (yyyy-mm-dd):");
        JTextField tfDate = new JTextField(LocalDate.now().toString(), 12);
        JLabel lblWeight = new JLabel("Weight (kg):");
        JTextField tfWeight = new JTextField(10);
        JLabel lblHeight = new JLabel("Height (cm):");
        JTextField tfHeight = new JTextField(10);
        JLabel lblSleep = new JLabel("Sleep (hours):");
        JTextField tfSleep = new JTextField(10);
        JLabel lblWater = new JLabel("Water (liters):");
        JTextField tfWater = new JTextField(10);
        JLabel lblCal = new JLabel("Calories:");
        JTextField tfCal = new JTextField(10);
        JLabel lblMood = new JLabel("Mood:");
        JComboBox<String> cbMood = new JComboBox<>(new String[]{"Happy", "Neutral", "Sad", "Anxious"});
        JLabel lblNotes = new JLabel("Notes:");
        JTextField tfNotes = new JTextField(15);

        JButton btnSave = new JButton("💾 Save Record");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setFocusPainted(false);
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        int y = 0;
        gc.gridx = 0; gc.gridy = y; form.add(lblUser, gc);
        gc.gridx = 1; form.add(tfUser, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblDate, gc);
        gc.gridx = 1; form.add(tfDate, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblWeight, gc);
        gc.gridx = 1; form.add(tfWeight, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblHeight, gc);
        gc.gridx = 1; form.add(tfHeight, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblSleep, gc);
        gc.gridx = 1; form.add(tfSleep, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblWater, gc);
        gc.gridx = 1; form.add(tfWater, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblCal, gc);
        gc.gridx = 1; form.add(tfCal, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblMood, gc);
        gc.gridx = 1; form.add(cbMood, gc);
        y++;
        gc.gridx = 0; gc.gridy = y; form.add(lblNotes, gc);
        gc.gridx = 1; form.add(tfNotes, gc);
        y++;
        gc.gridx = 1; gc.gridy = y; form.add(btnSave, gc);

        add(form, BorderLayout.CENTER);

        // Save button action
        btnSave.addActionListener(e -> {
            String username = tfUser.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username required!");
                return;
            }

            // Find or create user
            UserDAO udao = new UserDAO();
            User user = udao.findByUsername(username);
            if (user == null) {
                user = new User();
                user.setUsername(username);
                user.setPasswordHash(SimpleHash.sha256("default")); // default password
                user.setFullName(username);
                udao.create(user);
            }

            try {
                double weight = tfWeight.getText().isEmpty() ? 0 : Double.parseDouble(tfWeight.getText());
                double height = tfHeight.getText().isEmpty() ? 0 : Double.parseDouble(tfHeight.getText());
                double sleep = tfSleep.getText().isEmpty() ? 0 : Double.parseDouble(tfSleep.getText());
                double water = tfWater.getText().isEmpty() ? 0 : Double.parseDouble(tfWater.getText());
                int cal = tfCal.getText().isEmpty() ? 0 : Integer.parseInt(tfCal.getText());
                String mood = (String) cbMood.getSelectedItem();
                String notes = tfNotes.getText();

                HealthRecord rec = new HealthRecord();
                rec.setUserId(user.getId());
                rec.setRecordDate(tfDate.getText());
                rec.setWeightKg(weight);
                rec.setHeightCm(height);
                rec.setSleepHours(sleep);
                rec.setWaterLiters(water);
                rec.setCaloriesIntake(cal);
                rec.setMood(mood);
                rec.setNotes(notes);

                // Calculate BMI
                HealthService svc = new HealthService();
                svc.computeAndSetBMI(rec);

                // Save record
                HealthRecordDAO dao = new HealthRecordDAO();
                if (dao.save(rec)) {
                    JOptionPane.showMessageDialog(this,
                            "✅ Record saved successfully!\nBMI: " + rec.getBmi(),
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    tfWeight.setText(""); tfHeight.setText(""); tfSleep.setText("");
                    tfWater.setText(""); tfCal.setText(""); tfNotes.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to save record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values!");
            }
        });
    }
}
