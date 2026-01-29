package com.gym.ui;

import com.gym.models.User;
import com.gym.services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementPanel extends JPanel {
    private UserService userService;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagementPanel() {
        userService = new UserService();
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"ID", "Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        refreshUserList();

        add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showUserDialog(null));
        editButton.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                int userId = (int) tableModel.getValueAt(row, 0);
                User user = userService.getUserById(userId);
                showUserDialog(user);
            }
        });
        deleteButton.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                int userId = (int) tableModel.getValueAt(row, 0);
                userService.deleteUser(userId);
                refreshUserList();
            }
        });
    }

    private void refreshUserList() {
        tableModel.setRowCount(0);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(), user.getName(), user.getEmail(), user.getRole()
            });
        }
    }

    private void showUserDialog(User user) {
        JTextField nameField = new JTextField(user != null ? user.getName() : "");
        JTextField emailField = new JTextField(user != null ? user.getEmail() : "");
        JTextField passwordField = new JTextField(user != null ? user.getPassword() : "");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Admin", "Trainer", "Member"});
        if (user != null) roleBox.setSelectedItem(user.getRole());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Password:")); panel.add(passwordField);
        panel.add(new JLabel("Role:")); panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, user == null ? "Add User" : "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String emailInput = emailField.getText().trim();
        if (!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
    if (user == null) {
                User newUser = new User(0, nameField.getText(), emailField.getText(), passwordField.getText(), (String) roleBox.getSelectedItem());
                userService.registerUser(newUser);
            } else {
                user.setName(nameField.getText());
                String Email = emailField.getText().trim();
        if (!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
    user.setEmail(emailInput);
                user.setPassword(passwordField.getText());
                user.setRole((String) roleBox.getSelectedItem());
                userService.updateUser(user);
            }
            refreshUserList();
        }
    }
}