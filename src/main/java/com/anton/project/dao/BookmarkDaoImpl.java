package com.anton.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
		
		bookmark.setFolder(folder);
		
		em.persist(bookmark);
		em.flush();
	}

	public void update(int id, String desc, String url) {
		Bookmark bookmark = em.find(Bookmark.class, id);
		bookmark.setDesc(desc);
		bookmark.setUrl(url);
		
		em.flush();
	}

	public void delete(int id) {
		Bookmark bookmark = em.find(Bookmark.class, id);
		
		em.remove(bookmark);
		em.flush();
	}
	
	public List<Bookmark> getAllObjects(int folderId) {
		TypedQuery<Bookmark> query = em.createQuery("SELECT b FROM Bookmark b WHERE b.folder=:folder", Bookmark.class);
		
		Folder folder = em.find(Folder.class, folderId);
		query.setParameter("folder", folder);
		
		return query.getResultList();
	}

	public void insert(Bookmark folder) {
		// TODO Auto-generated method stub
		
	}

	public List<Bookmark> getAllObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBookmarkByUrl(String url) {
		TypedQuery<Bookmark> query = em.createQuery("SELECT b FROM Bookmark b WHERE b.url=:bookmarkUrl", Bookmark.class);

		query.setParameter("bookmarkUrl", url);
		
		List<Bookmark> list = query.getResultList();
		if (list.size() != 0) {
			return list.get(0).getBookmarkId();
		}
		else {
			return -1;
		}
	}
}
