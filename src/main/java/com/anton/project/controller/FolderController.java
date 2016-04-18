package com.anton.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.anton.project.backup.do_export.ExportFileType;
import com.anton.project.backup.do_export.ExportManager;
import com.anton.project.backup.do_import.ImportManager;
import com.anton.project.backup.do_import.ImportOption;
import com.anton.project.domain.Bookmark;
import com.anton.project.domain.Folder;
import com.anton.project.security.domain.User;
import com.anton.project.security.service.UserService;
import com.anton.project.security.util.SecurityUtil;
import com.anton.project.service.FolderService;
import com.anton.project.service.ImportService;
import com.anton.project.util.TitleExtractor;

@Controller
public class FolderController {
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ImportService importService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/folders/add", method=RequestMethod.POST)
	public @ResponseBody void addFolder(@RequestParam String name, @RequestParam int parentId) {
		System.out.println("Folder Name: " + name);
		System.out.println("parentId: " + parentId);
		
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
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView getAllFolders(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		System.out.println(SecurityUtil.getUsername());
		User user = userService.findByEmail(SecurityUtil.getUsername());		
		if (user != null) {
			session.setAttribute("nickname", user.getNickname());
		}
		
		ModelAndView model = new ModelAndView("index");
		
		List<Folder> list = folderService.getAllObjects();
		model.addObject("folders", folderService.getAllObjects());
		model.addObject("importOption", new ImportOption());
		
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
	public String doImport(@ModelAttribute ImportOption importOption, @RequestParam MultipartFile file) {
		try {
			List<Folder> folders = ImportManager.doImport(file.getInputStream());
			
			class Output {
				public void printFolders(List<Folder> folders) {
					System.out.println(folders.size());
					
					for (Folder f : folders) {
						System.out.println("__" + f.getName() + "__");
						
						for (Bookmark b : f.getBookmarks()) {
							System.out.println("	" + b.getDesc() + "  " + b.getUrl());
						}
						
						if (f.getChildren().size() != 0) {
							printFolders(f.getChildren());
						}
					}
				}
			}
			
			//new Output().printFolders(folders);
			
			
			if (importOption.getOption().equals("merge")) {
				importService.merge(folders, -1);
			}
			else if (importOption.getOption().equals("overwrite")) {
				importService.overwrite(folders);
			}
			else {
				//throw exception
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
}
