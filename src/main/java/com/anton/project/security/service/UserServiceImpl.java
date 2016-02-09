package com.anton.project.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.project.security.dao.UserDao;
import com.anton.project.security.domain.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	@Override
	public User findByEmail(String email) {
		return null;
	}

	@Override
	public void addUser(User user, String role) {
		userDao.addUser(user, role);
	}

	@Override
	public void deleteUser(String email) {
		User user = userDao.findByEmail(email);
		
		userDao.deleteUser(user);
	}

	@Override
	public void updateUser(String email, String nickname, String password) {
		User user = userDao.findByEmail(email);
		
		user.setNickname(nickname);
		user.setPassword(password);
		
		userDao.updateUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void changeUserStatus(String email, Boolean status) {
		User user = userDao.findByEmail(email);
		user.setEnabled(status);
		
		userDao.changeUserStatus(user);
	}
	
	public Boolean emailExists(String email) {
		return userDao.findByEmail(email)==null ? false : true;
	}
	
}
