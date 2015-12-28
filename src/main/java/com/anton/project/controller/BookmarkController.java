package com.anton.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.domain.Bookmark;
import com.anton.project.service.BookmarkService;

@Controller
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;
	
	@RequestMapping(value="/bookmarks/add", method=RequestMethod.POST)
	public String addBookmark(@RequestParam(name="id", required=true) int folderId, @ModelAttribute("bookmarkForm") Bookmark bookmark) {
		bookmarkService.addObject(folderId, bookmark);
		
		return "redirect:/bookmarks?id="+folderId;
	}
	
	@RequestMapping(value="/bookmarks/update", method=RequestMethod.POST)
	public String updateBookmark(@RequestParam Bookmark bookmark) {
		bookmarkService.updateObject(bookmark);
		
		return "bookmarks";
	}
	
	@RequestMapping(value="/bookmarks/delete", method=RequestMethod.POST)
	public String deleteBookmark(@RequestParam(name="id") int id, @RequestParam(name="folderId") int folderId) {
		bookmarkService.deleteObject(id);
		
		return "redirect:/bookmarks?id="+folderId;
	}
	
	@RequestMapping(value="/bookmarks", method=RequestMethod.GET)
	public ModelAndView getAllBookmarks(@RequestParam(name="id", required=true) int folderId) {
		ModelAndView model = new ModelAndView("bookmarks");
		
		model.addObject("bookmarks", bookmarkService.getAllObjects(folderId));
		model.addObject("folderId", folderId);
		model.addObject("bookmarkForm", new Bookmark());		
		
		return model;
	}	
}





















