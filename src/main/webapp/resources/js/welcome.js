$(document).ready(function() {
	$('#question, #answer, #paragraph1, #paragraph2, #paragraph3').each(function(i) {
		$(this).hide();
	});
	
	$('#question, #answer, #paragraph1, #paragraph2, #paragraph3').each(function(i) {
		$(this).delay(i * 1000).fadeIn(2000);
	});
	
	$("#loginForm").validate({
		rules: {
			email: {
				required: true,
				email: true
			},
			password: {
				required: true,
				minlength: 6
			}
		},
		errorClass: "invalid",
		validClass: "valid",
		errorPlacement: function(error, element){
			error.insertBefore(element);
			/*element.before(error)*/
		},
		highlight: function(element, errorClass, validClass) {			
			$(element).addClass(errorClass).removeClass(validClass);
			var n = this.numberOfInvalids();
			$("#form-container").css("height", 200 + 25*n + "px");
			$(element).parent("div").removeClass("form-group").addClass("form-group-invalid");
		},
		unhighlight: function(element, errorClass, validClass) {			
			$(element).removeClass(errorClass).addClass(validClass);
			var n = this.numberOfInvalids();
			$("#form-container").css("height", 200 + 25*n + "px");
			$(element).parent("div").removeClass("form-group-invalid").addClass("form-group");
		},
		submitHandler: function(form) {
			form.on("submit", function(event) {
				event.preventDefault();
				return false;
			});
		}
	});
	
	$("#signUpLink").click(function() {
		$("#message-container").children().remove();		
		$("#link-container").fadeOut(1000).delay(1000).remove();
		$("#loginForm").fadeOut(1000).delay(1000).remove();
		
		var csrf_header = $("meta[name='_csrf_header']").attr("content");
		var csrf_token = $("meta[name='_csrf']").attr("content");
		
		$('#form-container').css("height", "200px");
		$("#form-container").append(			
			$("<form/>", {id: "registerForm"}).append(
				$("<div/>", {class: "form-group"}).append(
					$("<input/>", {id: "nickname", type: "text", name: "nickname", placeholder: "Pick a username"})
				),
				$("<div/>", {class: "form-group"}).append(
					$("<input/>", {id: "email", type: "text", name: "email", placeholder: "Your email"})
				),
				$("<div/>", {class: "form-group"}).append(
					$("<input/>", {id: "password", type: "password", name: "password", placeholder: "Create a password"})
				),
				$("<div/>", {class: "form-group"}).append(
					$("<input/>", {type: "submit", value: "Sign Up!"})
				)
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
			errorClass: "invalid",
			validClass: "valid",
			errorPlacement: function(error, element){
				error.insertBefore(element);
				/*element.before(error)*/
			},
			highlight: function(element, errorClass, validClass) {			
			    $(element).addClass(errorClass).removeClass(validClass);
				$(element).parent("div").removeClass("form-group").addClass("form-group-invalid");
			},
			unhighlight: function(element, errorClass, validClass) {			
				$(element).removeClass(errorClass).addClass(validClass);
				$(element).parent("div").removeClass("form-group-invalid").addClass("form-group");
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
							
							$.ajax({
								type: "POST",
								url: "./register",
								data: {nickname: _nickname_, email: _email_, password: _password_},
								beforeSend: function(xhr) {
									xhr.setRequestHeader(csrf_header, csrf_token);
								},													
								success: function() {
									$("#message-container").append(
										$("<div/>",  {class: "logout-message"}).append(
											$("<p/>").html("Signed Up successfully")
										)
									);
									$("#message-container").find("div").delay(5000).fadeOut(2000, function() {
										$(this).remove();
									});

									var csrf_header = $("meta[name='_csrf_header']").attr("content");
									var csrf_token = $("meta[name='_csrf']").attr("content");
									
									console.log(csrf_header);
									console.log(csrf_token);

									$("#registerForm").remove();
									$('#form-container').css("height", "200px");
									$("#form-container").append(			
										$("<form/>", {id: "loginForm", action: login_url, method: "POST"}).append(
											$("<div/>", {class: "form-group"}).append(
												$("<input/>", {id: "email", type: "text", name: "email", placeholder: "Enter your email"})
											),
											$("<div/>", {class: "form-group"}).append(
												$("<input/>", {id: "password", type: "password", name: "password", placeholder: "Enter your password"})
											),				
											$("<input/>", {type: "hidden", name: csrf_header, value: csrf_token}),
											$("<div/>", {class: "form-group"}).append(
												$("<div/>", {id: "remember-me-container"}).append(
													$("<div/>", {id: "remember-me-input-container"}).append(
														$("<input/>", {id: "remember-me-input", name: "remember-me", type: "checkbox"})
													),
													$("<div/>", {id: "remember-me-label-container"}).append(
														$("<p/>", {id: "remember-me-label"}).html("Remember Me")
													)
												),
												$("<div/>", {class: "clear"})
											),											
											$("<div/>", {class: "form-group"}).append(
												$("<input/>", {type: "submit", value: "Sign In"})
											)
										).hide().fadeIn(1000)
									);
									
									$("#content-container").append(
										$("<div/>", {id: "link-container"}).append(
											$("<button/>", {id: "signUpLink"}).html("Sign Up")
										)
									).hide().fadeIn(1000);
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