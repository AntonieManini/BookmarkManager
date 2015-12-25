package com.anton.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.domain.Folder;
import com.anton.project.service.FolderService;

@Controller("/folders")
public class FolderController {
	@Autowired
	private FolderService folderService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addFolder(@RequestParam Folder folder) {
		folderService.addObject(folder);
		
		return "folders";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateFolder(@RequestParam Folder folder) {
		folderService.updateObject(folder);
		
		return "folders";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteFolder(@RequestParam int id) {
		folderService.deleteObject(id);
		
		return "folders";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView getAllFolders() {
		ModelAndView model = new ModelAndView("folders");
		
		model.addObject("folders", folderService.getAllObjects());
		
		return model;
	}
}
