package com.anton.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anton.project.dao.FolderDao;
import com.anton.project.domain.Folder;

@Service
public class FolderServiceImpl implements FolderService {
	@Autowired
	private FolderDao folderDao;

	public void addObject(int id, Folder t) {
		// TODO Auto-generated method stub
		
	}
	
	public void addObject(Folder folder) {
		folderDao.insert(folder);
	}

	public void deleteObject(int id) {
		folderDao.delete(id);
	}

	public void updateObject(Folder t) {
		// TODO Auto-generated method stub
		
	}

	public List<Folder> getAllObjects() {
		return folderDao.getAllObjects();
	}

	public List<Folder> getAllObjects(int id) {
		return null;
	}

}
