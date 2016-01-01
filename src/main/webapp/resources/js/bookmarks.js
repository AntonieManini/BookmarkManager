$(document).ready(function() {
	$(".editButton").click(function() {
		var row = $(this).parents('tr');
		var $desc_column = $(row).find(".bookmark_desc").parents("td");
		var $url_column = $(row).find(".bookmark_url").parents("td");
		
		console.log($(this).html());
		
		if ($(this).html() == "Edit") {
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
			$url_column.find("a").html("Link");
		}
		
		$(this).html($(this).html() == "Edit" ? "Update" : "Edit");
		
	});
	
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
});