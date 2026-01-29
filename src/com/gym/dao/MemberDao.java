package com.gym.dao;

import com.gym.models.Member;
import com.gym.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
    private Connection connection;

    public MemberDao() {
        this.connection = DatabaseUtil.getConnection();
    }
//Insert a new member into the database
    public boolean insert(Member member) {
        String sql = "INSERT INTO members (user_id, membership_type, join_date, status, trainer_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, member.getUserId());
            stmt.setString(2, member.getMembershipType());
            stmt.setString(3, member.getJoinDate());
            stmt.setString(4, member.getStatus());
            stmt.setInt(5, member.getTrainerId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    member.setMemberId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Member getById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractMember(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//Get all members from the database.
    public List<Member> getAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(extractMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
//update  member's details.
    public boolean update(Member member) {
        String sql = "UPDATE members SET membership_type=?, join_date=?, status=?, trainer_id=? WHERE member_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, member.getMembershipType());
            stmt.setString(2, member.getJoinDate());
            stmt.setString(3, member.getStatus());
            stmt.setInt(4, member.getTrainerId());
            stmt.setInt(5, member.getMemberId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//Delete a member by ID
    public boolean delete(int memberId) {
        String sql = "DELETE FROM members WHERE member_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Member extractMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberId(rs.getInt("member_id"));
        member.setUserId(rs.getInt("user_id"));
        member.setMembershipType(rs.getString("membership_type"));
        member.setJoinDate(rs.getString("join_date"));
        member.setStatus(rs.getString("status"));
        member.setTrainerId(rs.getInt("trainer_id"));
        return member;
    }
}