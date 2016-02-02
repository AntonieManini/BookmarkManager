package com.anton.project.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.project.dao.BookmarkDao;
import com.anton.project.dao.FolderDao;
import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;
import com.anton.project.security.util.SecurityUtil;

@Service
public class ImportService {
	@Autowired
	private FolderDao folderDao;
	
	@Autowired
	private BookmarkDao bookmarkDao;
	
	public void merge(List<Folder> folders, int parentFolderId) {
		for (Folder folder : folders) {
			int folderId = folderDao.getFolderByName(folder.getName());
			
			if (folderId == -1) {
				if (parentFolderId == -1) {
					folderDao.insert(folder);
				}
				else {
					folderDao.insert(folder, parentFolderId);
				}
				
				folderId = folderDao.getFolderByName(folder.getName());
			}
			
			Set<Bookmark> bookmarks = folder.getBookmarks();
			
			for(Bookmark bookmark : bookmarks) {
				int bookmarkId = bookmarkDao.getBookmark(bookmark.getDesc(), bookmark.getUrl(), folderId);
				
				if (bookmarkId == -1) {
					bookmarkDao.insert(bookmark, folderId);
				}					
			}
			
			if (folder.getChildren().size() > 0) {
				//making new LinkedList otherwise getting ConcurrentModificationException
				merge(new LinkedList<>(folder.getChildren()), folderId);
			}
		}
	}
	
	public void overwrite(List<Folder> newFolders) {
		List<Folder> currentFolders = folderDao.getAllObjects();
		
		for (Folder folder : currentFolders) {
			folderDao.delete(folder.getFolderId());
		}
		
		merge(newFolders, -1);
	}
}
