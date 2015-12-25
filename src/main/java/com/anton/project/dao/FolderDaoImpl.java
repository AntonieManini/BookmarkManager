package com.anton.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anton.project.domain.Folder;

@Repository
@Transactional
public class FolderDaoImpl implements FolderDao {

	private EntityManager em;
	
	@PersistenceContext
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public void insert(Folder folder) {
	}

	public void update(Folder folder) {
	}

	public void delete(int id) {
	}

}
