package com.anton.project.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserRole {
	private Integer userRoleId;	
	private String role;	
	private User user;
	
	@Id
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	@Column
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
