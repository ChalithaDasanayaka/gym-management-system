package com.gym.models;

public class Trainer {
    private int trainerId;
    private int userId;
    private String contact;
    private String specialization;
    private int experience;
    private double salary;
//default constructor
    public Trainer() {}

    public Trainer(int trainerId, int userId, String contact, String specialization, int experience, double salary) {
        this.trainerId = trainerId;
        this.userId = userId;
        this.contact = contact;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
    }
// // Getter and Setter methods 
	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

    
}