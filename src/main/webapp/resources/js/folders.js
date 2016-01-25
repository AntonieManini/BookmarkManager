$(document).ready(function() {
	$(".editButton").click(function() {
		var row = $(this).parents('tr');
		var $column = $(row).find(".folder_name").parents("td");
		
		if ($(this).html() == "Edit") {
			var folder_name = $(row).find(".folder_name").html();
			
			$column.children().remove();
			
			$column.append(
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
			
			$column.children().remove();
			
			$column.append(
				$("<a/>", {class: "folder_name", href: "/bookmarks?id="+folder_id})
			);			
			$column.find("a").html(folder_name);
		}
		
		$(this).html($(this).html() == "Edit" ? "Update" : "Edit");
		
	});
	
	
	$(".addFolderButton").click(function() {
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
			
			console.log(folder_name + " " + parent_id);
			var csrf_header = $("meta[name='_csrf_header']").attr("content");
			var csrf_token = $("meta[name='_csrf']").attr("content");
			
			$.ajax({
				type: "POST",
				url: "./folders/add",
				data: {name: folder_name, parentId: parent_id},
/*				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrf_header, csrf_token);
				}*/
			});
			
			$(".parent").removeClass("parent");
			$(".temporal").children().remove();
			$(".temporal").removeClass("temporal");
		});		
	});
});