$(document).ready(function() {
	$(".folder-row").draggable(
		{
			scope: "folders",
			revert: "invalid",
			stop: function(event, ui) {
				
			}
		}
	);
	
	$(".tree_content").droppable(
		{
			scope: "folders",
			drop: function(event, ui) {
				var csrf_header = $("meta[name='_csrf_header']").attr("content");
				var csrf_token = $("meta[name='_csrf']").attr("content");

				var child_id = $(ui.draggable).find(".folder_id").prop("value");

				$(this).find("#folder_list").append(
					$("<li/>").append(
						ui.draggable
					)
				);
				
				$.ajax({
					type: "POST",
					url: "folders/change_parent",
					data: {id: child_id, parent_id: 0},
					beforeSend: function(xhr) {
						xhr.setRequestHeader(csrf_header, csrf_token);
					},
					success: function() {
						
					}
				});				
			}
		}
	);
	
	$(".folder-row").droppable(
		{
			scope: "folders",			
			accept: ".folder-row",
			drop: function(event, ui) {
				var csrf_header = $("meta[name='_csrf_header']").attr("content");
				var csrf_token = $("meta[name='_csrf']").attr("content");

				var child_id = $(ui.draggable).find(".folder_id").prop("value");
				var parent_li = $(this).parent("li");
				var parent_id = $(parent_li).find(".folder_id").prop("value");
				var nested_ul = $(parent_li).find("ul");				
				
				console.log("Child ID: " + child_id);
				console.log("Parent ID: " + parent_id);
				
				if (nested_ul.length != 0) {
					$(nested_ul).append(
						$("<li/>").append(
							ui.draggable
						)
					);
					
					$.ajax({
						type: "POST",
						url: "folders/change_parent",
						data: {id: child_id, parent_id: parent_id},
						beforeSend: function(xhr) {
							xhr.setRequestHeader(csrf_header, csrf_token);
						},
						success: function() {
							
						}
					});
				}
				else {
					$(this).parent("li").append(
						$("<ul/>").append(
							$("<li/>").append(
								ui.draggable
							)
						)
					);
					
					$.ajax({
						type: "POST",
						url: "folders/change_parent",
						data: {id: child_id, parent_id: parent_id},
						beforeSend: function(xhr) {
							xhr.setRequestHeader(csrf_header, csrf_token);
						},
						success: function() {
							
						}
					});					
				}					
			}
		}
	);
	
	$(".collapsible_list").hide();
	
	$(".folder-icon").on("mouseover", function() {
		if ($(this).parents("li").first().find("ul").size() > 0) {
			$(this).css("cursor", "pointer");
		}			
	});
	
	$(".folder-icon").on("mouseout", function() {
		$(this).css("cursor", "auto");					
	});

	
	$(".folder-icon").on("click", function() {
		$(this).parents("li").first().find("ul").first().slideToggle();
	});
	
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
		
		if ($(this).hasClass("editButton")) {
			var folder_name = $(row).find(".folder_name").html();
			
			$(row).find(".folder_name").remove();
			$(row).find(".deleteButton").remove();
			$(row).find(".addSubFolderButton").remove();
			
			$(row).prepend(
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
			
			$(row).children().remove();
			
			var page_context = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
			$(row).append(
				$("<input/>", {class: "folder_id", type: "hidden", value: folder_id}),
				$("<a/>", {class: "folder_name", href: page_context+"/bookmarks?id="+folder_id}).html(folder_name),
				$("<button/>", {class: "editButton", style: "display:none"}),
				$("<button/>", {class: "deleteButton", style: "display:none"}),
				$("<button/>", {class: "addSubFolderButton", style: "display:none"})				
			);
			$("a.folder_name").click(function() {
				$(".tree_content").find(".current-folder").removeClass("current-folder");
		
				$(this).parent("div").addClass("current-folder").removeClass("mouseover");
				var folder_id = $(this).parent("div").find(".folder_id").prop("value");
		
				$("#bookmarkAddForm").show();
				getBookmarks(folder_id);
		
				return false;
			});
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
				$(row).closest("li").remove();
			}
		});
	});
	
	$(".addSubFolderButton").click(function() {
		var row = $(this).closest("li");
		
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