package com.anton.project.backup.do_import;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;

public class XmlImportProvider implements ImportProvider {

	public List<Folder> doImport(InputStream in) {
		List<Folder> list = new LinkedList<Folder>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			
			doc.getDocumentElement().normalize();
			
			NodeList folderList = ((Element)doc.getElementsByTagName("folders").item(0)).getChildNodes();
			
			for (int i = 0; i < folderList.getLength(); i++) {
				Node folderNode = folderList.item(i);
				
				if (folderNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) folderNode;
					
					String folderName = element.getElementsByTagName("name").item(0).getTextContent();
					
					Folder folder = new Folder();
					folder.setName(folderName);
					
					NodeList childNodesList = element.getChildNodes();
					for(int j = 0; j < childNodesList.getLength(); j++) {
						Node childNode = childNodesList.item(j);
						
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							if (childElement.getTagName().equals("bookmarks")) {						
								Set<Bookmark> bookmarks = new HashSet<Bookmark>();
		
								NodeList bookmarkList = childElement.getChildNodes();
								
								for (int k = 0; k < bookmarkList.getLength(); k++) {
									Node bookmarkNode = bookmarkList.item(k);
									
									if (bookmarkNode.getNodeType() == Node.ELEMENT_NODE) {
										Element bookmarkElement = (Element) bookmarkNode;
										
										String desc = bookmarkElement.getElementsByTagName("desc").item(0).getTextContent(); 
										String url = bookmarkElement.getElementsByTagName("url").item(0).getTextContent();
										
										Bookmark bookmark = new Bookmark();
										bookmark.setDesc(desc);
										bookmark.setUrl(url);
										
										bookmarks.add(bookmark);
									}
								}
								
								folder.setBookmarks(bookmarks);
							}
							else if (childElement.getTagName().equals("folders")) {
								folder.setChildren(getFolderChildren(childElement));
							}
						}					
					}
					
					list.add(folder);
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		
		return list;
	}
	
	
	
	private List<Folder> getFolderChildren(Element parentElement) {
		List<Folder> list = new LinkedList<>();
		
		NodeList folderList = parentElement.getChildNodes();
		
		for (int i = 0; i < folderList.getLength(); i++) {
			Node folderNode = folderList.item(i);
			
			if (folderNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) folderNode;
				
				String folderName = element.getElementsByTagName("name").item(0).getTextContent();
				
				Folder folder = new Folder();
				folder.setName(folderName);
				
				NodeList childNodesList = element.getChildNodes();
				for(int j = 0; j < childNodesList.getLength(); j++) {
					Node childNode = childNodesList.item(j);
					
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						if (childElement.getTagName().equals("bookmarks")) {						
							Set<Bookmark> bookmarks = new HashSet<Bookmark>();
	
							NodeList bookmarkList = childElement.getChildNodes();
							
							for (int k = 0; k < bookmarkList.getLength(); k++) {
								Node bookmarkNode = bookmarkList.item(k);
								
								if (bookmarkNode.getNodeType() == Node.ELEMENT_NODE) {
									Element bookmarkElement = (Element) bookmarkNode;
									
									String desc = bookmarkElement.getElementsByTagName("desc").item(0).getTextContent(); 
									String url = bookmarkElement.getElementsByTagName("url").item(0).getTextContent();
									
									Bookmark bookmark = new Bookmark();
									bookmark.setDesc(desc);
									bookmark.setUrl(url);
									
									bookmarks.add(bookmark);
								}
							}
							
							folder.setBookmarks(bookmarks);
						}
						else if (childElement.getTagName().equals("folders")) {
							folder.setChildren(getFolderChildren(childElement));
						}
					}					
				}
				
				list.add(folder);
			}
		}
		
		return list;
	}

}
