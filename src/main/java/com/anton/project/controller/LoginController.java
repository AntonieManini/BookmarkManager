package com.anton.project.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.anton.project.security.dao.UserDao;
import com.anton.project.security.domain.User;

@Controller("/")
public class LoginController {
	private UserDao userDao;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam User user) {
		return "folders";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@RequestParam User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		userDao.addUser(user, "USER");
		
		return "folders";		
	}
}
