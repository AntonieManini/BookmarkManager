package com.anton.project.dao;

import java.util.List;

public interface Crud<T> {
	void insert(T t);
	void insert(T t, int id);
	void update(T t);
	void delete(int id);
	List<T> getAllObjects();
	List<T> getAllObjects(int id);
}
