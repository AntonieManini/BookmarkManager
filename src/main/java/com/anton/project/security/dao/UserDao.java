package com.anton.project.security.dao;

import com.anton.project.security.domain.User;

public interface UserDao {
	User findByUserName(String username);
}
