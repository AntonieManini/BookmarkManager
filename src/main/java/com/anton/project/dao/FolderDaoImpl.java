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
import com.anton.project.security.util.SecurityUtil;

@Repository
@Transactional
public class FolderDaoImpl implements FolderDao {
	private EntityManager em;
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public Folder get(int id) {
		Folder result = em.find(Folder.class, id);
		
		if (result == null) result = new Folder();
		
		return result;
	}
	
	public void insert(Folder folder) {
		folder.setUsername(SecurityUtil.getUsername());
		
		em.persist(folder);
		em.flush();
	}
	
	public void insert(Folder folder, int parentId) {
		Folder parent = em.find(Folder.class, parentId);
		
		if (parent != null) folder.setParent(parent);
		parent.getChildren().add(folder);
		
		folder.setUsername(SecurityUtil.getUsername());
		
		em.persist(folder);
		em.flush();
	}	

	public void update(int id, String name) {
		Folder folder = em.find(Folder.class, id);
		folder.setName(name);
		
		em.flush();
	}
	
	public void update(int id, int newParentId) {
		Folder folder = this.get(id);
		
		if (newParentId != 0) {
			Folder newParent = this.get(newParentId);			
			folder.setParent(newParent);			
		}
		else {
			folder.setParent(null);
		}
		
		em.flush();
		
		refreshCollection();
	}

	public void delete(int id) {
		Folder folder = em.find(Folder.class, id);
		
		em.remove(folder);
	}
	
	public List<Folder> getAllObjects() {
		//System.out.println(SecurityUtil.getUsername());
		
		TypedQuery<Folder> query = em.createQuery("SELECT f FROM Folder f WHERE f.parent=NULL AND f.username=:user", Folder.class);
		query.setParameter("user", SecurityUtil.getUsername());		
		
		List<Folder> result = (List<Folder>)query.getResultList();
	
		return result;
	}

	public int getFolderByName(String name) {
		TypedQuery<Folder> query = em.createQuery("SELECT f FROM Folder f WHERE f.name=:folderName AND f.username=:userName", Folder.class);
		
		query.setParameter("folderName", name);
		query.setParameter("userName", SecurityUtil.getUsername());
		
		List<Folder> list = query.getResultList();
		if (list.size() != 0) {
			return list.get(0).getFolderId();
		}
		else {
			return -1;
		}
	}


	@Override
	public List<Folder> getAllObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void refreshCollection() {
		List<Folder> folders = getAllObjects();
		
		for (Folder folder: folders) {
			em.refresh(folder);
		}
	}
}
