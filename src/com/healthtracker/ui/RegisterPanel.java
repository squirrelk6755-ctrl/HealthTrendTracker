package com.healthtracker.ui;

import com.healthtracker.dao.UserDAO;
import com.healthtracker.model.User;
import com.healthtracker.util.SimpleHash;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    public RegisterPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.anchor = GridBagConstraints.WEST;

        JLabel lUser = new JLabel("Username:");
        JTextField tfUser = new JTextField(15);
        JLabel lPass = new JLabel("Password:");
        JPasswordField pf = new JPasswordField(15);
        JLabel lFull = new JLabel("Full name:");
        JTextField tfFull = new JTextField(15);
        JButton btnCreate = new JButton("Create Account");

        int row = 0;
        gc.gridx=0; gc.gridy=row; add(lUser, gc);
        gc.gridx=1; add(tfUser, gc);
        row++;
        gc.gridx=0; gc.gridy=row; add(lPass, gc);
        gc.gridx=1; add(pf, gc);
        row++;
        gc.gridx=0; gc.gridy=row; add(lFull, gc);
        gc.gridx=1; add(tfFull, gc);
        row++;
        gc.gridx=1; gc.gridy=row; add(btnCreate, gc);

        btnCreate.addActionListener(e -> {
            String username = tfUser.getText().trim();
            String pass = new String(pf.getPassword()).trim();
            String full = tfFull.getText().trim();
            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password required.");
                return;
            }
            User u = new User();
            u.setUsername(username);
            u.setPasswordHash(SimpleHash.sha256(pass));
            u.setFullName(full);
            UserDAO dao = new UserDAO();
            if (dao.create(u)) {
                JOptionPane.showMessageDialog(this, "User created. You may now enter records via 'Input Record'.");
                tfUser.setText("");
                pf.setText("");
                tfFull.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user. Username may already exist.");
            }
        });
    }
}
