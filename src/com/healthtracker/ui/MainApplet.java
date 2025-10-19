package com.healthtracker.ui;

import javax.swing.*;
import java.applet.Applet;

public class MainApplet extends Applet {
    @Override
    public void init() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
