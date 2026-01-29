package com.gym.ui;

import com.gym.models.Trainer;
import com.gym.models.User;
import com.gym.services.TrainerService;
import com.gym.services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrainerManagementPanel extends JPanel {
    private TrainerService trainerService;
    private UserService userService;
    private JTable trainerTable;
    private DefaultTableModel tableModel;

    public TrainerManagementPanel() {
        trainerService = new TrainerService();
        userService = new UserService();
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"Trainer ID", "Name", "Email", "Contact", "Specialization", "Experience", "Salary"};
        tableModel = new DefaultTableModel(columns, 0);
        trainerTable = new JTable(tableModel);
        refreshTrainerList();

        add(new JScrollPane(trainerTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Trainer");
        JButton editButton = new JButton("Edit Trainer");
        JButton deleteButton = new JButton("Delete Trainer");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showTrainerDialog(null));
        editButton.addActionListener(e -> {
            int row = trainerTable.getSelectedRow();
            if (row >= 0) {
                int trainerId = (int) tableModel.getValueAt(row, 0);
                Trainer trainer = trainerService.getTrainerById(trainerId);
                showTrainerDialog(trainer);
            }
        });
        deleteButton.addActionListener(e -> {
            int row = trainerTable.getSelectedRow();
            if (row >= 0) {
                int trainerId = (int) tableModel.getValueAt(row, 0);
                trainerService.deleteTrainer(trainerId);
                refreshTrainerList();
            }
        });
    }

    private void refreshTrainerList() {
        tableModel.setRowCount(0);
        List<Trainer> trainers = trainerService.getAllTrainers();
        for (Trainer trainer : trainers) {
            User user = userService.getUserById(trainer.getUserId());
            tableModel.addRow(new Object[]{
                trainer.getTrainerId(),
                user != null ? user.getName() : "",
                user != null ? user.getEmail() : "",
                trainer.getContact(),
                trainer.getSpecialization(),
                trainer.getExperience(),
                trainer.getSalary()
            });
        }
    }

    private void showTrainerDialog(Trainer trainer) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField specializationField = new JTextField();
        JTextField experienceField = new JTextField();
        JTextField salaryField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        if (trainer != null) {
            User user = userService.getUserById(trainer.getUserId());
            if (user != null) {
                nameField.setText(user.getName());
                emailField.setText(user.getEmail());
            }
            contactField.setText(trainer.getContact());
            specializationField.setText(trainer.getSpecialization());
            experienceField.setText(String.valueOf(trainer.getExperience()));
            salaryField.setText(String.valueOf(trainer.getSalary()));
        }

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Password:")); panel.add(passwordField);
        panel.add(new JLabel("Contact:")); panel.add(contactField);
        panel.add(new JLabel("Specialization:")); panel.add(specializationField);
        panel.add(new JLabel("Experience:")); panel.add(experienceField);
        panel.add(new JLabel("Salary:")); panel.add(salaryField);

        int result = JOptionPane.showConfirmDialog(this, panel, trainer == null ? "Add Trainer" : "Edit Trainer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String contactInput = contactField.getText().trim();
        if (!contactInput.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be exactly 10 digits.");
            return;
        }
    if (trainer == null) {
                // Create new user and trainer
                User newUser = new User(0, nameField.getText(), emailField.getText(), new String(passwordField.getPassword()), "Trainer");
                if (userService.registerUser(newUser)) {
                    Trainer newTrainer = new Trainer();
                    newTrainer.setUserId(newUser.getId());
                    newTrainer.setContact(contactField.getText());
                    newTrainer.setSpecialization(specializationField.getText());
                    newTrainer.setExperience(Integer.parseInt(experienceField.getText()));
                    newTrainer.setSalary(Double.parseDouble(salaryField.getText()));
                    trainerService.addTrainer(newTrainer);
                }
            } else {
                // Update existing trainer and user
                User user = userService.getUserById(trainer.getUserId());
                if (user != null) {
                    user.setName(nameField.getText());
                    user.setEmail(emailField.getText());
                    String password = new String(passwordField.getPassword());
                    if (!password.isEmpty()) user.setPassword(password);
                    userService.updateUser(user);
                }
                String Contact = contactField.getText().trim();
        if (!contactInput.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be exactly 10 digits.");
            return;
        }
    trainer.setContact(contactInput);
                trainer.setSpecialization(specializationField.getText());
                trainer.setExperience(Integer.parseInt(experienceField.getText()));
                trainer.setSalary(Double.parseDouble(salaryField.getText()));
                trainerService.updateTrainer(trainer);
            }
            refreshTrainerList();
        }
    }
}