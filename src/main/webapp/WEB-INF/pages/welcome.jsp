<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Welcome!</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/welcome.css"/>">
</head>
<body>
	<div id="greeting">
		<h3>Welcome to Burito!</h3>
		<p>Your personal Bookmark Manager</p>
	</div>
	
	<div id="info">
		<div id="paragraph1">
			<p>Tired of synchronizing your bookmarks between different browsers and computers?</p>
		</div>
		<div id="paragraph2">
			<p>We have an answer for you: Burito</p>
		</div>
		<div id="paragraph3">		
			<p>Create your bookmarks once, use everywhere</p>
		</div>
		<div id="paragraph4">
			<p>You can safely merge bookmarks from your friend without losing yours</p>
		</div>
		<div id="paragraph5">
			<p>Easy to use, minimal design</p>
		</div>
	</div>
	
	<div id="user-management">
		<a id="signUpLink" href="#">I want to Sign Up!</a>
		<form id="loginForm" action="<c:url value="/login" />" method="post">
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='email'></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" value="submit" /></td>
				</tr>
			</table>		
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
	
	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/resources/js/additional-methods.min.js"/>"></script>
	<script src="<c:url value="/resources/js/welcome.js"/>"></script>
</body>
</html>