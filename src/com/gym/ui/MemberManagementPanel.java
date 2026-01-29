package com.gym.ui;

import com.gym.models.Member;
import com.gym.models.User;
import com.gym.models.Trainer;
import com.gym.services.MemberService;
import com.gym.services.UserService;
import com.gym.services.TrainerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberManagementPanel extends JPanel {
    private MemberService memberService;
    private UserService userService;
    private TrainerService trainerService;
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public MemberManagementPanel() {
        memberService = new MemberService();
        userService = new UserService();
        trainerService = new TrainerService();
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"Member ID", "Name", "Email", "Membership Type", "Join Date", "Status", "Trainer"};
        tableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(tableModel);
        refreshMemberList();

        add(new JScrollPane(memberTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Member");
        JButton editButton = new JButton("Edit Member");
        JButton deleteButton = new JButton("Delete Member");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showMemberDialog(null));
        editButton.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row >= 0) {
                int memberId = (int) tableModel.getValueAt(row, 0);
                Member member = memberService.getMemberById(memberId);
                showMemberDialog(member);
            }
        });
        deleteButton.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row >= 0) {
                int memberId = (int) tableModel.getValueAt(row, 0);
                memberService.deleteMember(memberId);
                refreshMemberList();
            }
        });
    }

    private void refreshMemberList() {
        tableModel.setRowCount(0);
        List<Member> members = memberService.getAllMembers();
        for (Member member : members) {
            User user = userService.getUserById(member.getUserId());
            Trainer trainer = trainerService.getTrainerById(member.getTrainerId());
            tableModel.addRow(new Object[]{
                member.getMemberId(),
                user != null ? user.getName() : "",
                user != null ? user.getEmail() : "",
                member.getMembershipType(),
                member.getJoinDate(),
                member.getStatus(),
                trainer != null ? trainer.getSpecialization() : ""
            });
        }
    }

    private void showMemberDialog(Member member) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> membershipTypeBox = new JComboBox<>(new String[]{"Basic", "Premium", "VIP"});
        JTextField joinDateField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Inactive", "Suspended"});
        JComboBox<String> trainerBox = new JComboBox<>();
        List<Trainer> trainers = trainerService.getAllTrainers();
        for (Trainer t : trainers) {
            trainerBox.addItem(t.getTrainerId() + " - " + t.getSpecialization());
        }

        if (member != null) {
            User user = userService.getUserById(member.getUserId());
            if (user != null) {
                nameField.setText(user.getName());
                emailField.setText(user.getEmail());
            }
            membershipTypeBox.setSelectedItem(member.getMembershipType());
            joinDateField.setText(member.getJoinDate());
            statusBox.setSelectedItem(member.getStatus());
            for (int i = 0; i < trainers.size(); i++) {
                if (trainers.get(i).getTrainerId() == member.getTrainerId()) {
                    trainerBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Password:")); panel.add(passwordField);
        panel.add(new JLabel("Membership Type:")); panel.add(membershipTypeBox);
        panel.add(new JLabel("Join Date (YYYY-MM-DD):")); panel.add(joinDateField);
        panel.add(new JLabel("Status:")); panel.add(statusBox);
        panel.add(new JLabel("Trainer:")); panel.add(trainerBox);

        int result = JOptionPane.showConfirmDialog(this, panel, member == null ? "Add Member" : "Edit Member", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String emailInput = emailField.getText().trim();
        if (!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
    if (member == null) {
                // Create new user and member
                User newUser = new User(0, nameField.getText(), emailField.getText(), passwordField.getText(), "Member");
                if (userService.registerUser(newUser)) {
                    Member newMember = new Member();
                    newMember.setUserId(newUser.getId());
                    newMember.setMembershipType((String) membershipTypeBox.getSelectedItem());
                    newMember.setJoinDate(joinDateField.getText());
                    newMember.setStatus((String) statusBox.getSelectedItem());
                    newMember.setTrainerId(trainers.get(trainerBox.getSelectedIndex()).getTrainerId());
                    memberService.addMember(newMember);
                }
            } else {
                // Update existing member and user
                User user = userService.getUserById(member.getUserId());
                if (user != null) {
                    user.setName(nameField.getText());
                    String Email = emailField.getText().trim();
        if (!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
    user.setEmail(emailInput);
                    String password = passwordField.getText();
                    if (!password.isEmpty()) user.setPassword(password);
                    userService.updateUser(user);
                }
                member.setMembershipType((String) membershipTypeBox.getSelectedItem());
                member.setJoinDate(joinDateField.getText());
                member.setStatus((String) statusBox.getSelectedItem());
                member.setTrainerId(trainers.get(trainerBox.getSelectedIndex()).getTrainerId());
                memberService.updateMember(member);
            }
            refreshMemberList();
        }
    }
}