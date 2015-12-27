package com.anton.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;

@Repository
@Transactional
public class BookmarkDaoImpl implements BookmarkDao {

	private EntityManager em;
	
	@PersistenceContext
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void insert(Bookmark bookmark, int folderId) {
		Folder folder = em.find(Folder.class, folderId);
		folder.getBookmarks().add(bookmark);
		
		em.merge(folder);
	}

	public void update(Bookmark bookmark) {
		em.merge(bookmark);
	}

	public void delete(int id) {
		Bookmark bookmark = em.find(Bookmark.class, id);
		
		em.remove(bookmark);		
	}
	
	public List<Bookmark> getAllObjects(int folderId) {
		Query query = em.createNativeQuery("SELECT * FROM BOOKMARK WHERE FOLDER_ID=?", Bookmark.class);
		query.setParameter(1, folderId);
		
		return query.getResultList();
	}

	public void insert(Bookmark folder) {
		// TODO Auto-generated method stub
		
	}

	public List<Bookmark> getAllObjects() {
		// TODO Auto-generated method stub
		return null;
	}
}
