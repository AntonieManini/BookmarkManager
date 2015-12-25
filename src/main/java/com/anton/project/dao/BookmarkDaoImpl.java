package com.anton.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.anton.project.domain.Bookmark;

public class BookmarkDaoImpl implements BookmarkDao {

	private EntityManager em;
	
	@PersistenceContext
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void insert(Bookmark folder) {
		// TODO Auto-generated method stub
		
	}

	public void update(Bookmark folder) {
		// TODO Auto-generated method stub
		
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
