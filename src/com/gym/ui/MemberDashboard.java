package com.gym.ui;

import com.gym.models.User;

import javax.swing.*;
import java.awt.*;

public class MemberDashboard extends JFrame {
    private User admin;

    public MemberDashboard(User admin) {
        this.admin = admin;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard");
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
        JLabel welcomeLabel = new JLabel("Welcome, Admin " + admin.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(textColor);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(panelColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setLayout(new GridLayout(2, 3, 20, 20));

        // Create and style buttons
        JButton manageUsersButton = createStyledButton("Manage Users", buttonColor, buttonHoverColor, textColor);
        JButton manageTrainersButton = createStyledButton("Manage Trainers", buttonColor, buttonHoverColor, textColor);
        JButton manageMembersButton = createStyledButton("Manage Members", buttonColor, buttonHoverColor, textColor);
        JButton manageAttendanceButton = createStyledButton("Manage Attendance", buttonColor, buttonHoverColor, textColor);
        JButton logoutButton = createStyledButton("Logout", buttonColor, buttonHoverColor, textColor);

        // Add buttons to the panel
        buttonPanel.add(manageUsersButton);
        buttonPanel.add(manageTrainersButton);
        buttonPanel.add(manageMembersButton);
        buttonPanel.add(manageAttendanceButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Button action listeners
        manageUsersButton.addActionListener(e -> {
            JFrame frame = new JFrame("User Management");
            frame.setContentPane(new UserManagementPanel());
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        manageTrainersButton.addActionListener(e -> {
            JFrame frame = new JFrame("Trainer Management");
            frame.setContentPane(new TrainerManagementPanel());
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        manageAttendanceButton.addActionListener(e -> {
            JFrame frame = new JFrame("Attendance Management");
            frame.setContentPane(new AttendanceManagementPanel());
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        manageMembersButton.addActionListener(e -> {
            JFrame frame = new JFrame("Member Management");
            frame.setContentPane(new MemberManagementPanel());
            frame.setSize(900, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    // Helper method for styled buttons
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
