package com.anton.project.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.anton.project.security.dao.UserDao;
import com.anton.project.security.domain.*;

public class SecurityUserService implements UserDetailsService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public UserDetails loadUserByUsername(String username) {
		com.anton.project.security.domain.User user = userDao.findByUserName(username);
	
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
		
		return buildUserForAuthentication(user, authorities);
	}


	private User buildUserForAuthentication(com.anton.project.security.domain.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}


	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		
		for (UserRole role : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return new ArrayList<GrantedAuthority>(setAuths);
	}

}
