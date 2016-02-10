<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Welcome!</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/welcome.css"/>">
	<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
</head>
<body>
	<div id="wrapper">
		<div id="greeting">
			<div id="welcome-greeting"><p>Welcome to BURITO!</p></div>
			<div id="message"><p>Your personal Bookmark Manager</p></div>
		</div>	
		<div id="container">
			<div id="info">
				<div id="qa-container">
					<div id="question-container">
						<div id="question">
							<p>TIRED OF SYNCHRONIZING YOUR BOOKMARKS BETWEEN DIFFERENT BROWSERS AND COMPUTERS?</p>
						</div>
					</div>
					<div id="answer-container">
						<div id="answer">
							<p style="font-size: 46px;">We have an answer for you: <b><a href="#">BURITO</a></b></p>
						</div>
					</div>
				</div>
				<div id="list-container">
					<ul>
						<div id="paragraph1">		
							<li>Create your bookmarks once, use everywhere</li>
						</div>
						<div id="paragraph2">
							<li>You can safely merge bookmarks from your friend<br/>without losing yours</li>
						</div>
						<div id="paragraph3">
							<li>Minimal design, easy to use</li>
						</div>
					</ul>
				</div>					
			</div>
			
			<div id="user-management">
				<div id="content-container">
					<div id="form-container">
						<form id="loginForm" action="<c:url value='/login' />" method="post">
							<div>
								<label for="email"></label>
								<input type='text' name='email' placeholder="Enter your email">
							</div>
							<div>
								<label for="password"></label>
								<input type='password' name='password' placeholder="Enter your password"/>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div>
								<input name="submit" type="submit" value="Sign In" />
							</div>
						</form>
					</div>
					<div id="link-container">
						<button id="signUpLink">Sign Up</button>
					</div>				
				</div>
			</div>
		</div>	
		<div id="footer">
			<p>Copyright</p>
		</div>
	</div>
	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/resources/js/additional-methods.min.js"/>"></script>
	<script src="<c:url value="/resources/js/welcome.js"/>"></script>
</body>
</html>