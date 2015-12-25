package com.anton.project.service;

import java.util.List;

public interface CrudService<T> {
	void addObject(int id, T t);
	void addObject(T t);
	void deleteObject(int id);
	void updateObject(T t);
	List<T> getAllObjects();
	List<T> getAllObjects(int id);
}
