package com.anton.project.security.service;

import java.util.List;

import com.anton.project.security.domain.User;

public interface UserService {
	User findByEmail(String email);
	
	void addUser(User user, String role);
	
	void deleteUser(String email);
	
	void updateUser(String email, String nickname, String password);
	
	List<User> getAllUsers();
	
	void changeUserStatus(String email, Boolean status);
	
	Boolean emailExists(String email);
}
