package com.gym.ui;

import com.gym.models.Attendance;
import com.gym.models.Member;
import com.gym.services.AttendanceService;
import com.gym.services.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrainerAttendancePanel extends JPanel {
    private AttendanceService attendanceService;
    private MemberService memberService;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    public TrainerAttendancePanel(int trainerId) {
        attendanceService = new AttendanceService();
        memberService = new MemberService();
        setLayout(new BorderLayout());

        String[] columns = {"Attendance ID", "Member ID", "Check In", "Check Out", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        attendanceTable = new JTable(tableModel);
        add(new JScrollPane(attendanceTable), BorderLayout.CENTER);

        refreshAttendanceList(trainerId);
    }

    private void refreshAttendanceList(int trainerId) {
        tableModel.setRowCount(0);
        List<Member> members = memberService.getAllMembers();
        for (Member member : members) {
            if (member.getTrainerId() == trainerId) {
                List<Attendance> attendances = attendanceService.getAllAttendance();
                for (Attendance attendance : attendances) {
                    if (attendance.getMemberId() == member.getMemberId()) {
                        tableModel.addRow(new Object[]{
                            attendance.getAttendanceId(),
                            attendance.getMemberId(),
                            attendance.getCheckIn(),
                            attendance.getCheckOut(),
                            attendance.getDate()
                        });
                    }
                }
            }
        }
    }
}