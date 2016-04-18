package com.anton.project.service;

import java.util.List;
import com.anton.project.domain.Bookmark;



public interface BookmarkService {
	void addObject(int id, Bookmark t);
	void addObject(Bookmark t);
	void deleteObject(int id);
	void updateObject(int id, String desc, String url);	
	List<Bookmark> getAllObjects();
	List<Bookmark> getAllObjects(int id);	
}
