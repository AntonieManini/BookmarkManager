<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login Please</title>
	</head>
<body>
	<form name="loginForm" action="<c:url value="j_spring_security_check"/>" method="post">
		<input type="text" name="username"/>
		<input type="password" name="password"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="Submit"/>
	</form>
</body>
</html>