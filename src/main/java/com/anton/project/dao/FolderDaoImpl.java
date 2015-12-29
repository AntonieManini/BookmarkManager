package com.anton.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
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
		em.persist(folder);
		em.flush();		
	}

	public void update(int id, String name) {
		Folder folder = em.find(Folder.class, id);
		folder.setName(name);
		
		
		em.flush();
	}

	public void delete(int id) {
		Folder folder = em.find(Folder.class, id);
		
		em.remove(folder);
	}
	
	public List<Folder> getAllObjects() {
		Query query = em.createNativeQuery("SELECT * FROM FOLDER", Folder.class);
		
		return (List<Folder>)query.getResultList();
	}

	public void insert(Folder folder, int id) {
		// TODO Auto-generated method stub
		
	}

	public List<Folder> getAllObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
