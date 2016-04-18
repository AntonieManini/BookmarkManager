package com.anton.project.service;

import java.util.List;

import com.anton.project.domain.Folder;


public interface FolderService {
	void addObject(int id, Folder t);
	void addObject(Folder t);
	void addObject(Folder t, int parentId);
	void deleteObject(int id);
	void updateObject(int id, String name);	
	List<Folder> getAllObjects();
	List<Folder> getAllObjects(int id);
}
