package com.anton.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping(value="/admin/add", method=RequestMethod.POST)
	public void addUser() {
		
	}
	
	@RequestMapping(value="/admin/delete", method=RequestMethod.POST)
	public void deleteUser() {
		
	}
	
	@RequestMapping(value="/admin/update", method=RequestMethod.POST)
	public void updateUser() {
		
	}
	
	@RequestMapping(value="/admin/disable", method=RequestMethod.POST)
	public void disableUser() {
		
	}
	
	@RequestMapping(value="/admin/list", method=RequestMethod.POST)
	public void getAllUsers() {
		
	}
}
