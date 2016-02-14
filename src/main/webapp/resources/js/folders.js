$(document).ready(function() {
	$(".content").on("mouseover", function() {
		if (!$(this).hasClass("current-folder")) {
			$(this).addClass("mouseover");
		}
		$(this).find(".editButton").show();
		$(this).find(".deleteButton").show();
		$(this).find(".addSubFolderButton").show();
	});
	
	$(".content").on("mouseout", function() {
		if (!$(this).hasClass("current-folder")) {
			$(this).removeClass("mouseover");
		}
		$(this).find(".editButton").hide();
		$(this).find(".deleteButton").hide();
		$(this).find(".addSubFolderButton").hide();
	});
	
	
	$("#folderAddForm").on("submit", function(event) {
		event.target.checkValidity();
		
		var _name_ = $("#folderAddForm").find(".newFolderName").prop("value");		
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");

		$.ajax({
			type: "POST",
			url: "./folders/add",
			data: {name: _name_, parentId: 0},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function() {
				$("#folderAddForm").trigger("reset");
				window.location.reload();
			}
		});
		
		return false;
	});
	
	$(".editButton").click(function() {
		var row = $(this).parent('div');		
		var $pointer = $(row).find(".folder_id");
		
		if ($(this).hasClass("editButton")) {			
			var folder_name = $(row).find(".folder_name").html();
			$(row).find(".folder_name").remove();
			
			$pointer.after(
				$("<input/>", {class: "folder_name", type: "text", name: "name", value: folder_name})
			);
		} 
		else {
			var folder_id = $(row).find(".folder_id").attr("value");
			var folder_name = $(row).find(".folder_name").prop("value");
			var csrf_header = $("meta[name='_csrf_header']").attr("content");
			var csrf_token = $("meta[name='_csrf']").attr("content");
			
			$.ajax({
				type: "POST",
				url: "./folders/update",
				data: {name: folder_name, folderId: folder_id},
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrf_header, csrf_token);
				}
			});
			
			$(row).find(".folder_name").remove();
			
			$pointer.after(
				$("<a/>", {class: "folder_name", href: "/bookmarks?id="+folder_id})
			);			
			$(row).find("a").html(folder_name);
		}
		
		$(this).toggleClass("editButton updateButton");
	});
	
	$(".deleteButton").click(function() {
		var folder_id = $(this).parent("div").find(".folder_id").prop("value");
		var row = $(this).parent("div");
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		
		$.ajax({
			type: "POST",
			url: "./folders/delete",
			data: {id: folder_id},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},			
			success: function() {
				$(row).parent("li").remove();
			}
		});
	});
	
	$(".addSubFolderButton").click(function() {
		var row = $(this).parent("div").parent("li");
		
		$(row).addClass("parent");
		
		$(row).append(
			$("<ul/>").append(
				$("<li/>", {class: "temporal"}).append(
					$("<input/>", {type: "text", placeholder: "Enter Folder Name"}),
					$("<button/>", {class: "submitNewSubFolderButton"}).html("Submit")
				)
			)
		);
		
		$(".submitNewSubFolderButton").click(function() {
			var folder_name = $(".temporal").find("input").prop("value");
			var parent_id = $(".parent").find(".folder_id").prop("value");

			var csrf_header = $("meta[name='_csrf_header']").attr("content");
			var csrf_token = $("meta[name='_csrf']").attr("content");
			
			$.ajax({
				type: "POST",
				url: "./folders/add",
				data: {name: folder_name, parentId: parent_id},
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrf_header, csrf_token);
				},
				success: function() {
					window.location.reload();
				}
			});
			
			$(".parent").removeClass("parent");
			$(".temporal").children().remove();
			$(".temporal").removeClass("temporal");
		});		
	});
	
	$("a.folder_name").click(function() {
		$(".tree_content").find(".current-folder").removeClass("current-folder");
		
		$(this).parent("div").addClass("current-folder").removeClass("mouseover");
		var folder_id = $(this).parent("div").find(".folder_id").prop("value");
		
		$("#bookmarkAddForm").show();
		getBookmarks(folder_id);
		
		return false;
	});
});