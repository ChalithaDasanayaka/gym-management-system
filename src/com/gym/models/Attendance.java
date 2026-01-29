package com.gym.models;

public class Attendance {
    private int attendanceId;
    private int memberId;
    private String checkIn;  
    private String checkOut; 
    private String date;     
//Default constructor
    public Attendance() {}

    public Attendance(int attendanceId, int memberId, String checkIn, String checkOut, String date) {
        this.attendanceId = attendanceId;
        this.memberId = memberId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.date = date;
    }
// // Getter and setter methods
	public int getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

   
}