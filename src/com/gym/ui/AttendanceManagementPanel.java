package com.gym.ui;

import com.gym.models.Attendance;
import com.gym.models.Member;
import com.gym.services.AttendanceService;
import com.gym.services.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendanceManagementPanel extends JPanel {
    private AttendanceService attendanceService;
    private MemberService memberService;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    public AttendanceManagementPanel() {
        attendanceService = new AttendanceService();
        memberService = new MemberService();
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"Attendance ID", "Member id", "Check In", "Check Out", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        attendanceTable = new JTable(tableModel);
        refreshAttendanceList();

        add(new JScrollPane(attendanceTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Attendance");
        JButton editButton = new JButton("Edit Attendance");
        JButton deleteButton = new JButton("Delete Attendance");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showAttendanceDialog(null));
        editButton.addActionListener(e -> {
            int row = attendanceTable.getSelectedRow();
            if (row >= 0) {
                int attendanceId = (int) tableModel.getValueAt(row, 0);
                Attendance attendance = attendanceService.getAttendanceById(attendanceId);
                showAttendanceDialog(attendance);
            }
        });
        deleteButton.addActionListener(e -> {
            int row = attendanceTable.getSelectedRow();
            if (row >= 0) {
                int attendanceId = (int) tableModel.getValueAt(row, 0);
                attendanceService.deleteAttendance(attendanceId);
                refreshAttendanceList();
            }
        });
    }

    private void refreshAttendanceList() {
        tableModel.setRowCount(0);
        List<Attendance> attendances = attendanceService.getAllAttendance();
        for (Attendance attendance : attendances) {
            Member member = memberService.getMemberById(attendance.getMemberId());
            tableModel.addRow(new Object[]{
                attendance.getAttendanceId(),
                member != null ? member.getMemberId() + " - " + member.getMembershipType() : "",
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getDate()
            });
        }
    }

    private void showAttendanceDialog(Attendance attendance) {
        List<Member> members = memberService.getAllMembers();
        JComboBox<String> memberBox = new JComboBox<>();
        for (Member m : members) {
            memberBox.addItem(m.getMemberId() + " - " + m.getMembershipType());
        }
        JTextField checkInField = new JTextField(attendance != null ? attendance.getCheckIn() : "");
        JTextField checkOutField = new JTextField(attendance != null ? attendance.getCheckOut() : "");
        JTextField dateField = new JTextField(attendance != null ? attendance.getDate() : "");

        if (attendance != null) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getMemberId() == attendance.getMemberId()) {
                    memberBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Member:")); panel.add(memberBox);
        panel.add(new JLabel("Check In:")); panel.add(checkInField);
        panel.add(new JLabel("Check Out:")); panel.add(checkOutField);
        panel.add(new JLabel("Date:")); panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, attendance == null ? "Add Attendance" : "Edit Attendance", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int memberId = members.get(memberBox.getSelectedIndex()).getMemberId();
            if (attendance == null) {
                Attendance newAttendance = new Attendance();
                newAttendance.setMemberId(memberId);
                newAttendance.setCheckIn(checkInField.getText());
                newAttendance.setCheckOut(checkOutField.getText());
                newAttendance.setDate(dateField.getText());
                attendanceService.addAttendance(newAttendance);
            } else {
                attendance.setMemberId(memberId);
                attendance.setCheckIn(checkInField.getText());
                attendance.setCheckOut(checkOutField.getText());
                attendance.setDate(dateField.getText());
                attendanceService.updateAttendance(attendance);
            }
            refreshAttendanceList();
        }
    }
}