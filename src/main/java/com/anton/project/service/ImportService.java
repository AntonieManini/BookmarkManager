package com.anton.project.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.project.dao.BookmarkDao;
import com.anton.project.dao.FolderDao;
import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;

@Service
public class ImportService {
	@Autowired
	private FolderDao folderDao;
	
	@Autowired
	private BookmarkDao bookmarkDao;
	
	public void merge(List<Folder> folders) {
		for (Folder folder : folders) {
			int folderId = folderDao.getFolderByName(folder.getName());
			
			if (folderId == -1) {
				folderDao.insert(folder);
			}
			else {
				Set<Bookmark> bookmarks = folder.getBookmarks();
				
				for(Bookmark bookmark : bookmarks) {
					int bookmarkId = bookmarkDao.getBookmarkByUrl(bookmark.getUrl());
					
					if (bookmarkId == -1) {
						bookmarkDao.insert(bookmark, folderId);
					}					
				}
			}
		}
	}
	
	public void importAllObjects(List<Folder> folders) {
		
	}
}
