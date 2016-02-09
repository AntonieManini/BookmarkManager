$(document).ready(function() {
	$('#question, #answer, #paragraph1, #paragraph2, #paragraph3').each(function(i) {
		$(this).hide();
	});
	
	$('#question, #answer, #paragraph1, #paragraph2, #paragraph3').each(function(i) {
		$(this).delay(i * 1000).fadeIn(2000);
	});
	
	$("#signUpLink").click(function() {
		$("#signUpLink").fadeOut(1000).delay(1000).remove();
		$("#loginForm").fadeOut(1000).delay(1000).remove();
		
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		$("#user-management").append(			
			$("<form/>", {id: "registerForm"}).append(
				$("<label/>").attr("for", "nickname").html("Username: "),
				$("<input/>", {id: "nickname", type: "text", name: "nickname"}),
				$("<br/>"),
				$("<label/>").attr("for", "email").html("Email: "),
				$("<input/>", {id: "email", type: "text", name: "email"}),
				$("<br/>"),
				$("<label/>").attr("for", "password").html("Password: "),
				$("<input/>", {id: "password", type: "password", name: "password"}),
				$("<br/>"),
				$("<input/>", {type: "submit", value: "Sign Up!"})
			).hide().fadeIn(2000)
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