package com.anton.project.security.dao;

import java.util.List;

import com.anton.project.security.domain.User;

public interface UserDao {
	User findByUserName(String username);
	
	void addUser(User user, String role);
	
	void deleteUser();
	
	void updateUser();
	
	List<User> getAllUsers();
	
	void disableUser();
}
