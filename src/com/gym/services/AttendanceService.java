package com.gym.services;

import com.gym.dao.AttendanceDao;
import com.gym.models.Attendance;

import java.util.List;

public class AttendanceService {
    private AttendanceDao attendanceDao;
 // Constructor
    public AttendanceService() {
        this.attendanceDao = new AttendanceDao();
    }

    public boolean addAttendance(Attendance attendance) {
        return attendanceDao.insert(attendance);
    }
//  Get attendance record by ID
    public Attendance getAttendanceById(int attendanceId) {
        return attendanceDao.getById(attendanceId);
    }
    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceDao.getAll();
    }
    // Update  attendance record
    public boolean updateAttendance(Attendance attendance) {
        return attendanceDao.update(attendance);
    }
//delete attendance record
    public boolean deleteAttendance(int attendanceId) {
        return attendanceDao.delete(attendanceId);
    }
}