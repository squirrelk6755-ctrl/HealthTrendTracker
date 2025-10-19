package com.healthtracker.ui;

import com.healthtracker.dao.HealthRecordDAO;
import com.healthtracker.dao.UserDAO;
import com.healthtracker.model.HealthRecord;
import com.healthtracker.model.User;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel header = new JLabel("📊 Health Dashboard", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(46, 139, 87));
        add(header, BorderLayout.NORTH);

        JButton btnLoad = new JButton("🔄 Load User Data");
        btnLoad.setBackground(new Color(46, 139, 87));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLoad.setFocusPainted(false);
        add(btnLoad, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        btnLoad.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter username to view data:");
            if (username == null || username.trim().isEmpty()) return;

            UserDAO udao = new UserDAO();
            User user = udao.findByUsername(username.trim());
            if (user == null) {
                JOptionPane.showMessageDialog(this, "User not found!");
                return;
            }

            HealthRecordDAO dao = new HealthRecordDAO();
            List<HealthRecord> list = dao.findByUser(user.getId());
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records for this user.");
                return;
            }

            // Table
            String[] cols = {"Date", "Weight", "Height", "BMI", "Sleep", "Water", "Calories", "Mood"};
            Object[][] rows = new Object[list.size()][cols.length];
            for (int i = 0; i < list.size(); i++) {
                HealthRecord r = list.get(i);
                rows[i][0] = r.getRecordDate();
                rows[i][1] = r.getWeightKg();
                rows[i][2] = r.getHeightCm();
                rows[i][3] = r.getBmi();
                rows[i][4] = r.getSleepHours();
                rows[i][5] = r.getWaterLiters();
                rows[i][6] = r.getCaloriesIntake();
                rows[i][7] = r.getMood();
            }

            JTable table = new JTable(rows, cols);
            table.setRowHeight(25);
            table.setFont(new Font("SansSerif", Font.PLAIN, 14));
            JTableHeader th = table.getTableHeader();
            th.setBackground(new Color(46, 139, 87));
            th.setForeground(Color.WHITE);
            th.setFont(new Font("SansSerif", Font.BOLD, 14));

            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            // BMI Trend Chart
            java.util.List<Double> bmiValues = list.stream()
                    .map(r -> r.getBmi() == null ? 0.0 : r.getBmi())
                    .collect(Collectors.toList());
            TrendChart chart = new TrendChart(bmiValues);

            centerPanel.removeAll();
            centerPanel.add(scroll, BorderLayout.CENTER);
            centerPanel.add(chart, BorderLayout.SOUTH);
            centerPanel.revalidate();
            centerPanel.repaint();
        });
    }

    // Chart Panel
    static class TrendChart extends JPanel {
        private final java.util.List<Double> data;
        public TrendChart(java.util.List<Double> data) {
            this.data = data;
            setPreferredSize(new Dimension(800, 200));
            setBackground(Color.WHITE);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            int pad = 40;
            double max = data.stream().max(Double::compare).orElse(1.0);
            double min = data.stream().min(Double::compare).orElse(0.0);
            double range = (max - min) == 0 ? 1 : (max - min);
            g2.setColor(Color.GRAY);
            g2.drawRect(pad, pad, w - 2 * pad, h - 2 * pad);
            g2.setColor(new Color(46, 139, 87));
            for (int i = 0; i < data.size() - 1; i++) {
                int x1 = pad + (int) ((i / (double) (data.size() - 1)) * (w - 2 * pad));
                int x2 = pad + (int) (((i + 1) / (double) (data.size() - 1)) * (w - 2 * pad));
                int y1 = pad + (int) ((1 - (data.get(i) - min) / range) * (h - 2 * pad));
                int y2 = pad + (int) ((1 - (data.get(i + 1) - min) / range) * (h - 2 * pad));
                g2.drawLine(x1, y1, x2, y2);
                g2.fillOval(x1 - 3, y1 - 3, 6, 6);
            }
        }
    }
}
