
package com.gym.ui;

import com.gym.models.User;

import javax.swing.*;
import java.awt.*;

public class TrainerDashboard extends JFrame {
    private User trainer;
    private JTabbedPane tabbedPane;

    public TrainerDashboard(User trainer) {
        this.trainer = trainer;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Trainer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Welcome Panel
        JPanel welcomePanel = createWelcomePanel();
        add(welcomePanel, BorderLayout.NORTH);

        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Attendance", new TrainerAttendancePanel(trainer.getId()));
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, Trainer " + trainer.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel);
        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        statsPanel.add(new JLabel("Total Members: 0"));
        statsPanel.add(new JLabel("Today's Attendance: 0"));
        panel.add(statsPanel);

        // Quick Actions Panel
        JPanel quickActionsPanel = new JPanel(new GridLayout(2, 1));
        JButton recordAttendanceButton = new JButton("Record Attendance");
        recordAttendanceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Record Attendance functionality not implemented yet.");
        });
        quickActionsPanel.add(recordAttendanceButton);
        panel.add(quickActionsPanel);

        return panel;
    }
}
