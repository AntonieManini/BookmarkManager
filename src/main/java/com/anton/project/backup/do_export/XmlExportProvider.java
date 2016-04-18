package com.anton.project.backup.do_export;

import java.io.PrintWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;

public class XmlExportProvider implements ExportProvider {

	@Override
	public void doExport(List<Folder> folders, PrintWriter writer) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("folders");
			doc.appendChild(rootElement);
			
			for (Folder folder : folders) {
				Element folderElement = doc.createElement("folder");
				
				Element folderName = doc.createElement("name");
				folderName.appendChild(doc.createTextNode(folder.getName()));
				
				folderElement.appendChild(folderName);
				
				if (folder.getBookmarks().size() != 0) {
					Element bookmarksElement = doc.createElement("bookmarks");
					
					for (Bookmark bookmark : folder.getBookmarks()) {
						Element bookmarkElement = doc.createElement("bookmark");
						
						Element bookmarkDesc = doc.createElement("desc");
						bookmarkDesc.appendChild(doc.createTextNode(bookmark.getDesc()));

						Element bookmarkUrl = doc.createElement("url");
						bookmarkUrl.appendChild(doc.createTextNode(bookmark.getUrl()));
						
						bookmarkElement.appendChild(bookmarkDesc);
						bookmarkElement.appendChild(bookmarkUrl);
						bookmarksElement.appendChild(bookmarkElement);
					}
					
					folderElement.appendChild(bookmarksElement);
				}
				
				if (folder.getChildren().size() != 0) {
					addFolderChildren(folder.getChildren(), folderElement, doc);
				}
				
				rootElement.appendChild(folderElement);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(writer);
			
			transformer.transform(source, result);
		}
		catch (ParserConfigurationException e) {
		}
		catch (TransformerException e) {
		}		
	}
	
	public void addFolderChildren(List<Folder> children, Element parentFolderElement, Document doc) {
		Element foldersElement = doc.createElement("folders");
		
		for (Folder folder : children) {
			Element folderElement = doc.createElement("folder");
			
			Element folderName = doc.createElement("name");
			folderName.appendChild(doc.createTextNode(folder.getName()));
			
			folderElement.appendChild(folderName);
			
			if (folder.getBookmarks().size() != 0) {
				Element bookmarksElement = doc.createElement("bookmarks");
				
				for (Bookmark bookmark : folder.getBookmarks()) {
					Element bookmarkElement = doc.createElement("bookmark");
					
					Element bookmarkDesc = doc.createElement("desc");
					bookmarkDesc.appendChild(doc.createTextNode(bookmark.getDesc()));

					Element bookmarkUrl = doc.createElement("url");
					bookmarkUrl.appendChild(doc.createTextNode(bookmark.getUrl()));
					
					bookmarkElement.appendChild(bookmarkDesc);
					bookmarkElement.appendChild(bookmarkUrl);
					bookmarksElement.appendChild(bookmarkElement);
					
					System.out.println("Bookmark Desc: " + bookmark.getDesc());
				}
				
				folderElement.appendChild(bookmarksElement);
			}
			
			if (folder.getChildren().size() != 0) {
				addFolderChildren(folder.getChildren(), folderElement, doc);
			}			
			
			foldersElement.appendChild(folderElement);				
		}
		
		parentFolderElement.appendChild(foldersElement);
	}

}
