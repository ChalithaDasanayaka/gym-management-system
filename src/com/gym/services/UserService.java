package com.gym.services;

import com.gym.dao.UserDao;
import com.gym.models.User;

import java.util.List;

public class UserService {
	private UserDao userDao;

	public UserService() {
		this.userDao = new UserDao();
	}
	 // Register a new user
	public boolean registerUser(User user) {
		return userDao.insert(user);
	}
	 // Check login using email and password
	public User authenticate(String email, String password) {
		User user = userDao.getByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
	 // Get user by ID
	public User getUserById(int id) {
		return userDao.getById(id);
	}

	public List<User> getAllUsers() {
		return userDao.getAll();
	}
	// Update user details
	public boolean updateUser(User user) {
		return userDao.update(user);
	}

	public boolean deleteUser(int id) {
		return userDao.delete(id);
	}
}