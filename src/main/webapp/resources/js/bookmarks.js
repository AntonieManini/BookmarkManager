$(document).ready(function() {
	$("#inputUrl").bind("paste", function (e) {
		var pastedUrl = e.originalEvent.clipboardData.getData("text");
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		
		$.ajax({
			type: "POST",
			url: "./bookmarks/title",
			data: {url: pastedUrl},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function(data) {
				$("#inputDesc").prop("value", data);
			}
		});
	});
	
	$("#bookmarkAddForm").on("submit", function(event) {
		event.target.checkValidity();
		
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		var _desc_ = $("#bookmarkAddForm").find("#inputDesc").prop("value");
		var _url_ = $("#bookmarkAddForm").find("#inputUrl").prop("value");
		var folderId = $(".list_content").find(".folderId").prop("value");
		
		$.ajax({
			type: "POST",
			url: "./bookmarks/add",
			data: {desc: _desc_, url: _url_, id: folderId},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function() {
				$("#bookmarkAddForm").trigger("reset");

				getBookmarks(folderId);
			}
		});
		
		return false;
	});
	
	$("#bookmarkAddForm").hide();
});

function getBookmarks(folder_id) {
	$.ajax({
		type: "GET",
		url: "./bookmarks",
		data: {id: folder_id},
		success: function(data) {
			$(".list_content").find("table").children().remove();
		
			$(".list_content").find("table").prepend(
				$("<input/>", {class: "folderId", type: "hidden", value: folder_id})
			);
			
			for (b of data) {
				$(".list_content").find("table").append(
					$("<tr/>").append(
						$("<td/>").append(
							$("<input/>", {class: "bookmark_id", type: "hidden", value: b.bookmarkId})
						),
						$("<td/>").append(
							$("<a/>", {class: "bookmark_url", href: b.url, target: "_blank"}).html("Go")
						),						
						$("<td/>").append(
							$("<p/>", {class: "bookmark_desc"}).html(b.desc)
						),
						$("<td/>").append(
							$("<button/>", {class: "editButton"})
						),
						$("<td/>").append(
							$("<button/>", {class: "deleteButton"})
						),
						$("<td/>").append(
							$("<p/>", {class: "bookmark_url_p"}).html(b.url)
						)						
					)
				);
			}
			
			
			$(".editButton").click(function() {
				var row = $(this).parents('tr');
				var $desc_column = $(row).find(".bookmark_desc").parents("td");
				var $url_column = $(row).find(".bookmark_url").parents("td");
				
				if ($(this).hasClass("editButton")) {
					var bookmark_desc = $(row).find(".bookmark_desc").html();
					var bookmark_url = $(row).find(".bookmark_url").attr("href");
					
					$desc_column.children().remove();
					$url_column.children().remove();


					$desc_column.append(
						$("<input/>", {class: "bookmark_desc", type: "text", name: "desc", value: bookmark_desc})
					);
					
					$url_column.append(
						$("<input/>", {class: "bookmark_url", type: "text", name: "url", value: bookmark_url})
					);
				} 
				else {
					var bookmark_id = $(row).find(".bookmark_id").attr("value");
					var bookmark_desc = $(row).find(".bookmark_desc").prop("value");
					var bookmark_url = $(row).find(".bookmark_url").prop("value");
					var csrf_header = $("meta[name='_csrf_header']").attr("content");
					var csrf_token = $("meta[name='_csrf']").attr("content");
					
					$.ajax({
						type: "POST",
						url: "./bookmarks/update",
						data: {bookmarkId: bookmark_id, desc: bookmark_desc, url: bookmark_url},
						beforeSend: function(xhr) {
							xhr.setRequestHeader(csrf_header, csrf_token);
						}
					});
					
					
					$desc_column.children().remove();
					$url_column.children().remove();

					
					$desc_column.append(
						$("<p/>", {class: "bookmark_desc"})
					);			
					$desc_column.find("p").html(bookmark_desc);
					
					$url_column.append(
						$("<a/>", {class: "bookmark_url", href: bookmark_url, target: "_blank"})
					);			
					$url_column.find("a").html("Go");
				}
				
				$(this).toggleClass("editButton updateButton");				
			});
	
	
			$(".deleteButton").click(function() {
				var bookmark_row = $(this).parents("tr");
				
				$(bookmark_row).addClass("toRemove");
				var bookmark_id = $(bookmark_row).find(".bookmark_id").attr("value");
				var csrf_header = $("meta[name='_csrf_header']").attr("content");
				var csrf_token = $("meta[name='_csrf']").attr("content");
			
				$.ajax({
					type: "POST",
					url: "./bookmarks/delete",
					data: {id: bookmark_id},			
					beforeSend: function(xhr) {
						xhr.setRequestHeader(csrf_header, csrf_token);
					},
					success: function() {
						$(".toRemove").remove();
					}
				});
			});	
					
		}
	});
}