package com.anton.project.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="FOLDER")
public class Folder {
	private Integer folderId;
	private String name;
	private Set<Bookmark> bookmarks = new HashSet<Bookmark>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FOLDER_ID", length=4, nullable=false)
	public Integer getFolderId() {
		return folderId;
	}
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}
	
	@Column(name="NAME", length=20, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch=FetchType.LAZY)
	@Column(name = "BOOKMARK_ID", table="BOOKMARK")
	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}
	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	
}
