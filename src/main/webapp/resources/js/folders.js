$(document).ready(function() {
	$("#addFolderSubmit").on("click", function() {
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
				window.location.reload();
			}
		});
	});
	
	$(".editButton").click(function() {
		var row = $(this).parent('li');		
		var $pointer = $(row).find(".folder_id");
		
		if ($(this).html() == "Edit") {
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
		
		$(this).html($(this).html() == "Edit" ? "Update" : "Edit");
		
	});
	
	$(".deleteButton").click(function() {
		var folder_id = $(this).parent("li").find(".folder_id").prop("value");
		var row = $(this).parent("li");
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
				$(row).remove();
			}
		});
	});
	
	$(".addSubFolderButton").click(function() {
		var row = $(this).parent("li");
		
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
		var folder_id = $(this).parent("li").find(".folder_id").prop("value");
		
		$("#bookmarkAddForm").show();
		getBookmarks(folder_id);
		
		return false;
	});
});