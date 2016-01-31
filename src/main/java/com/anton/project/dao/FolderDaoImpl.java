package com.anton.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;

@Repository
@Transactional
public class FolderDaoImpl implements FolderDao {
	private EntityManager em;
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}

	
	public void insert(Folder folder) {		
		em.persist(folder);
		em.flush();		
	}
	
	public void insert(Folder folder, int parentId) {
		Folder parent = em.find(Folder.class, parentId);
		
		if (parent != null) folder.setParent(parent);
		parent.getChildren().add(folder);
		
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
		TypedQuery<Folder> query = em.createQuery("SELECT f FROM Folder f WHERE f.parent=NULL", Folder.class);
		
		List<Folder> result = (List<Folder>)query.getResultList();
		return result;
	}


	public List<Folder> getAllObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFolderByName(String name) {
		TypedQuery<Folder> query = em.createQuery("SELECT f FROM Folder f WHERE f.name=:folderName", Folder.class);
		
		query.setParameter("folderName", name);
		
		List<Folder> list = query.getResultList();
		if (list.size() != 0) {
			return list.get(0).getFolderId();
		}
		else {
			return -1;
		}
	}
}
