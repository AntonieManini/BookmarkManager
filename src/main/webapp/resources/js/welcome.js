$(document).ready(function() {
	$("#signUpLink").click(function() {
		$("#signUpLink").fadeOut(200)
		$("#loginForm").fadeOut(200);
	
		$("#signUpLink").remove();
		$("#loginForm").remove();
		
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		$("#user-management").append(			
			$("<form/>", {id: "registerForm", action: "./register", method: "POST"}).append(
				$("<input/>", {id: "nickname", type: "text", name: "nickname"}),
				$("<input/>", {id: "email", type: "text", name: "email"}),
				$("<input/>", {id: "password", type: "password", name: "password"}),
				$("<input/>", {type: "submit", value: "Sign Up!"})
			)
		);
		
		$("#registerForm").validate({
			rules: {
				email: {
					required: true,
					email: true
				},
				nickname: {
					required: true
				},
				password: {
					required: true,
					minlength: 6
				}
			},
			messages: {
				email: "Please enter a valid email",
				nickname: "Please enter an alphanumeric Name",
				password: "Please enter min 6 length password"
			},
			onfocusout: function(element, event) {
				if (element.name == "email") {
					console.log("you can check now");
/*				
					var _email_ = $("#registerForm").find("#email").prop("value");
					
					$.ajax({
						type: "POST",
						url: "./register/validate",
						data: {email: _email_},
						success: function() {
						}
					});*/
				}
				else return true;
			},
			submitHandler: function(form) {
				var csrf_header = $("meta[name='_csrf_header']").attr("content");
				var csrf_token = $("meta[name='_csrf']").attr("content");
			
				var _email_ = $("#registerForm").find("#email").prop("value");
				
				$.ajax({
					type: "GET",
					url: "./register/validate",
					data: {email: _email_},
					beforeSend: function(xhr) {
						xhr.setRequestHeader(csrf_header, csrf_token);
					},					
					success: function(data) {
						if (data == true) alert("Email exists!");
						else {
							var csrf_header = $("meta[name='_csrf_header']").attr("content");
							var csrf_token = $("meta[name='_csrf']").attr("content");
						
							var _nickname_ = $("#registerForm").find("#nickname").prop("value");
							var _email_ = $("#registerForm").find("#email").prop("value");
							var _password_ = $("#registerForm").find("#password").prop("value");
							
							console.log(_nickname_);
							console.log(_email_);
							console.log(_password_);
							
							$.ajax({
								type: "POST",
								url: "./register",
								data: {nickname: _nickname_, email: _email_, password: _password_},
								beforeSend: function(xhr) {
									xhr.setRequestHeader(csrf_header, csrf_token);
								},													
								success: function() {
									alert("Successfully Added a User!");
								},
								error: function() {
									alert("Error!");
								}
							});
						}
					}
				});
				
				return false;
			}
		});
	});
});