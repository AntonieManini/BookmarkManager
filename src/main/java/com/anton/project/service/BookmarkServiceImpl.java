package com.anton.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.project.dao.BookmarkDao;
import com.anton.project.domain.Bookmark;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	@Autowired
	BookmarkDao bookmarkDao;
	
	public void addObject(int folderId, Bookmark bookmark) {
		bookmarkDao.insert(bookmark, folderId);
	}

	public void addObject(Bookmark t) {
		// TODO Auto-generated method stub
		
	}

	public void deleteObject(int id) {
		bookmarkDao.delete(id);
	}

	public void updateObject(int id, String desc, String url) {
		// TODO Auto-generated method stub
		
	}

	public List<Bookmark> getAllObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Bookmark> getAllObjects(int folderId) {
		return bookmarkDao.getAllObjects(folderId);
	}
}
