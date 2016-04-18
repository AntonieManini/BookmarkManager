package com.anton.project.security.dao;

import java.util.List;

import com.anton.project.security.domain.User;

public interface UserDao {
	User findByEmail(String email);
	
	void addUser(User user, String role);
	
	void deleteUser(User user);
	
	void updateUser(User user);
	
	List<User> getAllUsers();
	
	void changeUserStatus(User user);
}
