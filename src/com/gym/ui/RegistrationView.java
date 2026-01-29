package com.gym.ui;

import com.gym.models.User;
import com.gym.services.UserService;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private UserService userService;

    public RegistrationView() {
        userService = new UserService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gym Management System - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Theme colors
        Color bgColor = new Color(34, 40, 49);
        Color panelColor = new Color(57, 62, 70);
        Color textColor = new Color(238, 238, 238);
        Color buttonColor = new Color(0, 173, 181);
        Color buttonHoverColor = new Color(0, 143, 150);

        getContentPane().setBackground(bgColor);

        JLabel headerLabel = new JLabel("Create Your Account");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(textColor);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(panelColor);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(textColor);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(textColor);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(textColor);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Member", "Trainer"});
        roleBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(roleBox);

        JButton registerButton = createStyledButton("Register", buttonColor, buttonHoverColor, textColor);
        registerButton.addActionListener(e -> handleRegister());
        panel.add(registerButton);
        
        JButton exitButton = createStyledButton("Exit", buttonColor, buttonHoverColor, textColor);
        exitButton.addActionListener(e -> {
            new LoginView().setVisible(true);  // Show the Login page
            dispose();  // Close the current registration window
        });
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

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

    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();

        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 8 characters long,\ncontain uppercase, lowercase, digit, and special character.",
                    "Weak Password", JOptionPane.WARNING_MESSAGE);
            return;
        }
       

        User user = new User(0, name, email, password, role);
        if (userService.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            new LoginView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may already be registered.");
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$");
    }
}
