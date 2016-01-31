package com.anton.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.anton.project.backup.do_export.ExportFileType;
import com.anton.project.backup.do_export.ExportManager;
import com.anton.project.backup.do_import.ImportManager;
import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;
import com.anton.project.service.FolderService;
import com.anton.project.service.ImportService;
import com.anton.project.util.TitleExtractor;

@Controller
public class FolderController {
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ImportService importService;
	
	@RequestMapping(value="/folders/add", method=RequestMethod.POST)
	public @ResponseBody void addFolder(@RequestParam String name, @RequestParam int parentId) {
		Folder folder = new Folder();
		folder.setName(name);
		
		if (parentId != 0) {
			folderService.addObject(folder, parentId);
		}
		else {
			folderService.addObject(folder);
		}
	}
	
	@RequestMapping(value="/folders/update", method=RequestMethod.POST)
	public @ResponseBody void updateFolder(@RequestParam(name="folderId") int folderId, @RequestParam(name="name") String name) {
		folderService.updateObject(folderId, name);
	}
	
	@RequestMapping(value="/folders/delete", method=RequestMethod.POST)
	public @ResponseBody void deleteFolder(@RequestParam int id) {
		folderService.deleteObject(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView getAllFolders() {
		ModelAndView model = new ModelAndView("index");
		
		List<Folder> list = folderService.getAllObjects();
		for (Folder f : list) {
			System.out.println(f.getName());
		}
		model.addObject("folders", folderService.getAllObjects());
		
		return model;
	}
	
	@RequestMapping(value="/folders/export", method=RequestMethod.GET)
	public void doExport(HttpServletResponse response) throws IOException {		
		List<Folder> folders = folderService.getAllObjects();
		
		response.setHeader("content-type", "text/xml");
		response.setHeader("content-disposition", "attachment; filename=export.xml");
		ExportManager.doExport(folders, "xml", response.getWriter());
	}
	
	@RequestMapping(value="/folders/import", method=RequestMethod.POST)
	public @ResponseBody String doImport(@RequestParam(name="file") MultipartFile file) {
		try {
			List<Folder> folders = ImportManager.doImport(file.getInputStream());
			
			for (Folder folder : folders) {
				System.out.println(folder.getName());
				
				for (Bookmark bookmark : folder.getBookmarks()) {
					System.out.println("  " + bookmark.getDesc());
					System.out.println("  " + bookmark.getUrl());
				}
				
				System.out.println();
			}
			
			importService.merge(folders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
