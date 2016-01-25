package com.anton.project.dao;

import java.util.List;

import com.anton.project.domain.Folder;

public interface FolderDao {
	void insert(Folder t);
	void insert(Folder t, int parentId);
	void update(int id, String name);
	void delete(int id);
	List<Folder> getAllObjects();
	List<Folder> getAllObjects(int id);
	int getFolderByName(String name);
}
