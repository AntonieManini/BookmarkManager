package com.anton.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anton.project.domain.Bookmark;
import com.anton.project.service.BookmarkService;
import com.anton.project.util.TitleExtractor;

@Controller
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;
	
	/*
	@RequestMapping(value="/bookmarks/add", method=RequestMethod.POST)
	public String addBookmark(@RequestParam(name="id", required=true) int folderId, @ModelAttribute("bookmarkForm") Bookmark bookmark) {
		bookmarkService.addObject(folderId, bookmark);
		
		return "redirect:/bookmarks?id="+folderId;
	}
	*/

	
	@RequestMapping(value="/bookmarks/add", method=RequestMethod.POST)
	public @ResponseBody void addBookmark(@RequestParam(name="desc") String desc, @RequestParam(name="url") String url, @RequestParam(name="id", required=true) int folderId) {
		Bookmark bookmark = new Bookmark();
		bookmark.setDesc(desc);
		bookmark.setUrl(url);
		
		System.out.println(desc);
		System.out.println(url);
		System.out.println(folderId);
		
		bookmarkService.addObject(folderId, bookmark);
	}
	
	
	@RequestMapping(value="/bookmarks/update", method=RequestMethod.POST)
	public @ResponseBody void updateBookmark(@RequestParam(name="bookmarkId") int bookmarkId, 
								 @RequestParam(name="desc") String desc,
								 @RequestParam(name="url") String url, @RequestParam(name="folder_id") int folderId) {
		bookmarkService.updateObject(bookmarkId, desc, url, folderId);
	}
	
	@RequestMapping(value="/bookmarks/delete", method=RequestMethod.POST)
	public @ResponseBody void deleteBookmark(@RequestParam(name="id") int id) {
		bookmarkService.deleteObject(id);
	}

/*	
	@RequestMapping(value="/bookmarks", method=RequestMethod.GET)
	public ModelAndView getAllBookmarks(@RequestParam(name="id", required=true) int folderId) {
		ModelAndView model = new ModelAndView("bookmarks");
		
		model.addObject("bookmarks", bookmarkService.getAllObjects(folderId));
		model.addObject("folderId", folderId);
		model.addObject("bookmarkForm", new Bookmark());		
		
		return model;
	}
*/

	@RequestMapping(value="/bookmarks", method=RequestMethod.GET)
	public @ResponseBody List<Bookmark> getAllBookmarks(@RequestParam(name="id", required=true) int folderId) {
		return bookmarkService.getAllObjects(folderId);
	}
	
	@RequestMapping(value="/bookmarks/title", method=RequestMethod.POST)
	public @ResponseBody String getTitle(@RequestParam(name="url") String url) {
		try {
			return TitleExtractor.getPageTitle(url);
		} catch (IOException e) {
			e.printStackTrace();
			
			return "";
		}
	}
}





















