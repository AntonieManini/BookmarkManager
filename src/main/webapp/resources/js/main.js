$(document).ready(function() {
	$(".editButton").click(function() {
		var row = $(this).parents('tr');
		var $column = $(row).find(".folder_name").parents("td");
		
		if ($(this).html() == "Edit") {
			var folder_name = $(row).find(".folder_name").html();
			
			$column.find("a").remove();
			
			$column.append(
				$("<input/>", {class: "folder_name", type: "text", name: "name", value: folder_name})
			);
		} 
		else {
			var folder_id = $(row).find(".folder_id").attr("value");
			var folder_name = $(row).find(".folder_name").prop("value");
			var csrf_header = $("meta[name='_csrf_header']").attr("content");
			var csrf_token = $("meta[name='_csrf']").attr("content");
			
			console.log(folder_id);
			console.log(folder_name);
			
			$.ajax({
				type: "POST",
				url: "./folders/update",
				data: {name: folder_name, folderId: folder_id},
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrf_header, csrf_token);
				}
			});
			
			$column.children().remove();
			
			$column.append(
				$("<a/>", {class: "folder_name", href: "/bookmarks?id="+folder_id})
			);			
			$column.find("a").html(folder_name);
		}
		
		$(this).html($(this).html() == "Edit" ? "Update" : "Edit");
		
	});
});