package com.anton.project.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hello.world.dao.UserDao;
import com.hello.world.model.UserRole;

public class SecurityUserService implements UserDetailsService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public UserDetails loadUserByUsername(String username) {
		com.hello.world.model.User user = userDao.findByUserName(username);
	
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		
		return buildUserForAuthentication(user, authorities);
	}


	private User buildUserForAuthentication(com.hello.world.model.User user, List<GrantedAuthority> authorities) {
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
