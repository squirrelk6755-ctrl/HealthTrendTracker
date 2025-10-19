package com.healthtracker.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainFrame() {
        setTitle("🏥 Health Trend Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        Color primary = new Color(46, 139, 87);   // green
        Color bg = new Color(250, 250, 250);      // soft white
        Color hover = new Color(200, 255, 200);   // light mint

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(primary);
        sidebar.setLayout(new GridLayout(6, 1, 0, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        // Sidebar buttons
        JButton btnHome = makeButton("🏠 Home", primary, hover);
        JButton btnRegister = makeButton("🧑 Register", primary, hover);
        JButton btnInput = makeButton("✍️ Input Record", primary, hover);
        JButton btnDashboard = makeButton("📊 Dashboard", primary, hover);
        JButton btnExit = makeButton("❌ Exit", primary, hover);

        sidebar.add(btnHome);
        sidebar.add(btnRegister);
        sidebar.add(btnInput);
        sidebar.add(btnDashboard);
        sidebar.add(new JLabel());
        sidebar.add(btnExit);

        // Top Bar
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(primary);
        JLabel title = new JLabel("  Health Trend Tracker", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        top.add(title, BorderLayout.WEST);

        // Cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(bg);
        cardPanel.add(new WelcomePanel(), "home");
        cardPanel.add(new RegisterPanel(), "register");
        cardPanel.add(new InputPanel(), "input");
        cardPanel.add(new DashboardPanel(), "dashboard");

        add(sidebar, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // Actions
        btnHome.addActionListener(e -> cardLayout.show(cardPanel, "home"));
        btnRegister.addActionListener(e -> cardLayout.show(cardPanel, "register"));
        btnInput.addActionListener(e -> cardLayout.show(cardPanel, "input"));
        btnDashboard.addActionListener(e -> cardLayout.show(cardPanel, "dashboard"));
        btnExit.addActionListener(e -> System.exit(0));
    }

    private JButton makeButton(String text, Color base, Color hover) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(base);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(hover);
                b.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(base);
                b.setForeground(Color.WHITE);
            }
        });
        return b;
    }
}
