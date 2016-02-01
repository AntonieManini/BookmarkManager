<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Sign Up</title>
</head>
<body>
	<c:url value="/register" var="signUpUrl"/>
	
	<form:form name="signUpForm" action="${signUpUrl}" method="post" commandName="user">
		<table>
			<tr>
				<td>User Name:</td>
				<td><form:input type='text' path='username' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:input type='password' path='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" /></td>
			</tr>
	  	</table>		
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form:form>
</body>
</html>