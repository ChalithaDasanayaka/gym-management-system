package com.gym.models;

public class Member {
    private int memberId;
    private int userId;
    private String membershipType;
    private String joinDate; 
    private String status;
    private int trainerId;
//Default constructor.
    public Member() {}

    public Member(int memberId, int userId, String membershipType, String joinDate, String status, int trainerId) {
        this.memberId = memberId;
        this.userId = userId;
        this.membershipType = membershipType;
        this.joinDate = joinDate;
        this.status = status;
        this.trainerId = trainerId;
    }

    @Override
	public String toString() {
		return "Member [memberId=" + memberId + "]";
	}
    // // Getter and Setter methods 
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

  
}