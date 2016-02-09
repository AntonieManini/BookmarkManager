package com.anton.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.security.domain.User;
import com.anton.project.security.service.UserService;

@Controller
public class AdminController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/admin/add", method=RequestMethod.POST)
	public String addUser(@RequestParam String nickname, @RequestParam String email, @RequestParam String password) {
		User user = new User();
		user.setNickname(nickname);
		user.setEmail(email);
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));		
		user.setEnabled(true);
		
		userService.addUser(user, "USER");
		
		return "redirect:/admin/list";
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/delete", method=RequestMethod.POST)
	public void deleteUser(String email) {
		userService.deleteUser(email);
	}
	
	@RequestMapping(value="/admin/update", method=RequestMethod.POST)
	public void updateUser(String email, String nickname, String password) {
		
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/disable", method=RequestMethod.POST)
	public void disableUser(@RequestParam String email, @RequestParam Boolean status) {
		userService.changeUserStatus(email, status);
	}
	
	@RequestMapping(value="/admin/list", method=RequestMethod.GET)
	public ModelAndView getAllUsers() {
		ModelAndView model = new ModelAndView("admin");
		
		model.addObject("users", userService.getAllUsers());
		
		return model;
	}
}
