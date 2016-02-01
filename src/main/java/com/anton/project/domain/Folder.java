package com.anton.project.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="FOLDER")
public class Folder {
	private Integer folderId;
	private String name;
	
	private Folder parent;
	
	private String username;
	private List<Folder> children = new LinkedList<Folder>();
	
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
	
	@Column(name="NAME", length=45, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="folder")
	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}
	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	public Folder getParent() {
		return parent;
	}
	public void setParent(Folder parent) {
		this.parent = parent;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="parent")
	public List<Folder> getChildren() {
		return children;
	}
	public void setChildren(List<Folder> children) {
		this.children = children;
	}
	
	@Column(name="USERNAME", length=45, nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
