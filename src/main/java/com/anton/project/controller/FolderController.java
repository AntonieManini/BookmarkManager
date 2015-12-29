package com.anton.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import com.anton.project.domain.Folder;
import com.anton.project.service.FolderService;

@Controller
public class FolderController {
	@Autowired
	private FolderService folderService;
	
	@RequestMapping(value="/folders/add", method=RequestMethod.POST)
	public String addFolder(@RequestParam String name) {
		Folder folder = new Folder();
		folder.setName(name);
		
		folderService.addObject(folder);
		
		return "redirect:/folders";
	}
	
	@RequestMapping(value="/folders/update", method=RequestMethod.POST)
	public String updateFolder(@RequestParam(name="folderId") int folderId, @RequestParam(name="name") String name) {
		folderService.updateObject(folderId, name);
		
		return "";
	}
	
	@RequestMapping(value="/folders/delete", method=RequestMethod.POST)
	public String deleteFolder(@RequestParam int id) {
		folderService.deleteObject(id);
		
		return "redirect:/folders";
	}
	
	@RequestMapping(value="/folders", method=RequestMethod.GET)
	public ModelAndView getAllFolders() {
		ModelAndView model = new ModelAndView("folders");
		
		model.addObject("folders", folderService.getAllObjects());
		
		return model;
	}
	
	@RequestMapping(value="/folders/export", method=RequestMethod.POST)
	public void export(HttpServletResponse response) throws IOException {
		response.setHeader("content-type", "text/plain");
		response.setHeader("content-disposition", "attachment; filename=export.txt");
		response.getWriter().write("Hello World");
	}
}
