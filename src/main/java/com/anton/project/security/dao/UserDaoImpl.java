package com.anton.project.security.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anton.project.security.domain.User;
import com.anton.project.security.domain.UserRole;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory; 
	
	public User findByUserName(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.username=:username");
		query.setParameter("username", username);
		
		return (User)query.list().get(0);
	}

	public void addUser(User user, String role) {
		UserRole userRole = new UserRole();
		userRole.setRole("ROLE_" + role);
		userRole.setUser(user);
		
		user.getRoles().add(userRole);
		
		Session session = sessionFactory.getCurrentSession();
		session.persist(user);
		session.persist(userRole);
	}
	
	public void deleteUser() {
		
	}

	@Override
	public void updateUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disableUser() {
		// TODO Auto-generated method stub
		
	}
}
