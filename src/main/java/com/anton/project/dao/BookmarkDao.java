package com.anton.project.dao;

import java.util.List;

import com.anton.project.domain.Bookmark;

public interface BookmarkDao {
	void insert(Bookmark t);
	void insert(Bookmark t, int id);
	void update(int id, String desc, String url);
	void delete(int id);
	List<Bookmark> getAllObjects();
	List<Bookmark> getAllObjects(int id);

}
