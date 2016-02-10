package com.anton.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.security.dao.UserDao;
import com.anton.project.security.domain.User;
import com.anton.project.security.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/welcome", method=RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Please verify your email and password");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("welcome");

		return model;
	}

	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView model = new ModelAndView("register");
		model.addObject("user", new User());
		
		return model;
	}
	/*
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@ModelAttribute User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));		
		user.setEnabled(true);
		
		userService.addUser(user, "USER");
		
		return "redirect:/";		
	}*/
	
	@ResponseBody
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public void register(@RequestParam String nickname, @RequestParam String email, @RequestParam String password) {
		User user = new User();
		user.setNickname(nickname);
		user.setEmail(email);
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));		
		user.setEnabled(true);
		
		userService.addUser(user, "USER");	
	}
	
	
	@ResponseBody
	@RequestMapping(value="/register/validate", method=RequestMethod.GET)
	public Boolean validateEmail(@RequestParam String email) {
		return userService.emailExists(email);
	}	
}
