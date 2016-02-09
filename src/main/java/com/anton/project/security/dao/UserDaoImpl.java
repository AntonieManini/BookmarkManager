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
	
	public User findByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.email=:email");
		query.setParameter("email", email);
		
		return query.list().size() != 0 ? (User)query.list().get(0) : null;
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
	

	@Override
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

	@Override
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().delete(user);;		
	}

	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void changeUserStatus(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
}
