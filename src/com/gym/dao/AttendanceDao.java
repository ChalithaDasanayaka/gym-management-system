package com.gym.dao;

import com.gym.models.Attendance;
import com.gym.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    private Connection connection;

    public AttendanceDao() {
        this.connection = DatabaseUtil.getConnection();
    }
//insert attendance data
    public boolean insert(Attendance attendance) {
        String sql = "INSERT INTO attendance (member_id, check_in, check_out, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, attendance.getMemberId());
            stmt.setString(2, attendance.getCheckIn());
            stmt.setString(3, attendance.getCheckOut());
            stmt.setString(4, attendance.getDate());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    attendance.setAttendanceId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Attendance getById(int attendanceId) {
        String sql = "SELECT * FROM attendance WHERE attendance_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAttendance(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
  //Retrieves all attendance records from the database
    public List<Attendance> getAll() {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                attendances.add(extractAttendance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }
//update attendance records
    public boolean update(Attendance attendance) {
        String sql = "UPDATE attendance SET member_id=?, check_in=?, check_out=?, date=? WHERE attendance_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attendance.getMemberId());
            stmt.setString(2, attendance.getCheckIn());
            stmt.setString(3, attendance.getCheckOut());
            stmt.setString(4, attendance.getDate());
            stmt.setInt(5, attendance.getAttendanceId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//delete attendance records
    public boolean delete(int attendanceId) {
        String sql = "DELETE FROM attendance WHERE attendance_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Attendance extractAttendance(ResultSet rs) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(rs.getInt("attendance_id"));
        attendance.setMemberId(rs.getInt("member_id"));
        attendance.setCheckIn(rs.getString("check_in"));
        attendance.setCheckOut(rs.getString("check_out"));
        attendance.setDate(rs.getString("date"));
        return attendance;
    }
}