package com.anton.project.security.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anton.project.security.domain.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory; 
	
	public User findByUserName(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.username=:username");
		query.setParameter("username", username);
		
		return (User)query.list().get(0);
	}

	public void addUser(User user, String role) {
	}

}
