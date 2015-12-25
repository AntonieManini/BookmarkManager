package com.anton.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.domain.Bookmark;
import com.anton.project.service.BookmarkService;

@Controller("/bookmarks")
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addBookmark(@RequestParam(name="id", required=true) int folderId, @RequestParam Bookmark bookmark) {
		bookmarkService.addObject(folderId, bookmark);
		
		return "bookmarks";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateBookmark(@RequestParam Bookmark bookmark) {
		bookmarkService.updateObject(bookmark);
		
		return "bookmarks";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteBookmark(@RequestParam int id) {
		bookmarkService.deleteObject(id);
		
		return "bookmarks";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView getAllBookmarks(@RequestParam int folderId) {
		ModelAndView model = new ModelAndView("bookmarks");
		
		model.addObject("bookmarks", bookmarkService.getAllObjects(folderId));
		
		return model;
	}	
}





















