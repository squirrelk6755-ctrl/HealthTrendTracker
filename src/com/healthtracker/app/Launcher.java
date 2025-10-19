package com.healthtracker.app;

import com.healthtracker.util.DBInit;
import com.healthtracker.ui.MainFrame;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {

        // 🌟 Step 1: Apply Nimbus Look and Feel for a modern UI
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Nimbus LookAndFeel not available, using default.");
        }

        // 🌟 Step 2: Initialize database and tables
        DBInit.createDatabaseAndTables();

        // 🌟 Step 3: Launch the main window
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
