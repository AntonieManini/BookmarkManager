package com.anton.project.dao;

public interface Crud<T> {
	void insert(T folder);
	void update(T folder);
	void delete(int id);
}
