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

public class AssignedMembersPanel extends JPanel {
    private MemberService memberService;
    private UserService userService;
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public AssignedMembersPanel(int trainerId) {
        memberService = new MemberService();
        userService = new UserService();
        setLayout(new BorderLayout());

        String[] columns = {"Member ID", "Name", "Email", "Membership Type", "Join Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(tableModel);
        add(new JScrollPane(memberTable), BorderLayout.CENTER);

        refreshMemberList(trainerId);
    }

    private void refreshMemberList(int trainerId) {
        tableModel.setRowCount(0);
        List<Member> members = memberService.getAllMembers();
        for (Member member : members) {
            if (member.getTrainerId() == trainerId) {
                User user = userService.getUserById(member.getUserId());
                tableModel.addRow(new Object[]{
                    member.getMemberId(),
                    user != null ? user.getName() : "",
                    user != null ? user.getEmail() : "",
                    member.getMembershipType(),
                    member.getJoinDate(),
                    member.getStatus()
                });
            }
        }
    }
}