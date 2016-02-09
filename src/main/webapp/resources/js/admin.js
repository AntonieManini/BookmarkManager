$(document).ready(function() {	
	$(".updateButton").on("click", function() {
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");

		$.ajax({
			type: "POST",
			url: "./update",
			data: {name: _name_, parentId: 0},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function() {
				
			}
		});	
	});
	
	$(".disableButton").on("click", function() {
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		var $row = $(this).parents('tr');		
		var _email_ = $row.find(".email").html();
		var _status_ = $row.find(".disableButton").html() == "Freeze" ? true : false;
		var _new_status_ = !_status_;
		
		console.log(_new_status_);
		$.ajax({
			type: "POST",
			url: "./disable",
			data: {email: _email_, status: _new_status_},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function() {
				if (_status_) {
					$row.find(".status").html("Frozen");
					$row.find(".disableButton").html("Activate");				
				}
				else {
					$row.find(".status").html("Active");
					$row.find(".disableButton").html("Freeze");								
				}
			},
			error: function() {
				alert("Couldn't disable User");
			}
		});
	
	});

	$(".deleteButton").on("click", function() {
		var $row = $(this).parents('tr');
		var _email_ = $row.find(".email").html();
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");

		console.log(_email_);
		$.ajax({
			type: "POST",
			url: "./delete",
			data: {email: _email_},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrf_header, csrf_token);
			},
			success: function() {
				$row.remove();
			},
			error: function() {
				alert("Couldn't delete User");
			}
		});	
	});
	
});