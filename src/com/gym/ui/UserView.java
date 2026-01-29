package com.gym.ui;

import com.gym.models.User;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {
    private User member;

    // Constructor 
    public UserView(User member) {
        this.member = member;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Member Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set theme colors
        Color bgColor = new Color(34, 40, 49);
        Color panelColor = new Color(57, 62, 70);
        Color textColor = new Color(238, 238, 238);
        Color buttonColor = new Color(0, 173, 181);
        Color buttonHoverColor = new Color(0, 143, 150);

        // Set background color for the frame
        getContentPane().setBackground(bgColor);

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + member.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(textColor);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel for user details
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(new GridLayout(7, 1));
        userDetailsPanel.setBackground(panelColor);
        userDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Display user's membership details and additional details (ID, Email, Password)
        JLabel idLabel = createStyledLabel("ID: " + member.getId(), textColor);
        JLabel emailLabel = createStyledLabel("Email: " + member.getEmail(), textColor);
        JLabel passwordLabel = createStyledLabel("Password: " + member.getPassword(), textColor);

        // Add the labels to the user details panel
        userDetailsPanel.add(idLabel);
        userDetailsPanel.add(emailLabel);
        userDetailsPanel.add(passwordLabel);

        // Add the user details panel to the center of the frame
        add(userDetailsPanel, BorderLayout.CENTER);

        // Logout button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(buttonColor);
        logoutButton.setForeground(textColor);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(buttonHoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(buttonColor);
            }
        });

        buttonPanel.add(logoutButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Logout button action listener
        logoutButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    // Helper method for styled labels
    private JLabel createStyledLabel(String text, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(textColor);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }
}
