package com.healthtracker.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));

        JLabel title = new JLabel("Welcome to Health Trend Tracker", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(46, 139, 87));

        JLabel sub = new JLabel("<html><center>Track your health metrics,<br>visualize trends, and stay motivated.</center></html>", JLabel.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(Color.DARK_GRAY);

        JPanel center = new JPanel(new GridLayout(3, 1));
        center.setBackground(new Color(245, 255, 245));
        center.setBorder(BorderFactory.createLineBorder(new Color(46, 139, 87), 2));
        center.add(title);
        center.add(sub);

        JButton getStarted = new JButton("🚀 Get Started");
        getStarted.setBackground(new Color(46, 139, 87));
        getStarted.setForeground(Color.WHITE);
        getStarted.setFont(new Font("Segoe UI", Font.BOLD, 18));
        getStarted.setFocusPainted(false);
        getStarted.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Use the sidebar to begin!")
        );

        center.add(getStarted);
        add(center, BorderLayout.CENTER);
    }
}
