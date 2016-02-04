<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin Page</title>
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>	
</head>
<body>
	<form id="" action="/admin/add" method="post">
		<input type="text" name="nick"/>
		<input type="text" name="email"/>
		<input type="password" name="password"/>
		<input type="submit" value="Add New User"/>
	</form>

	<table>
		<tr>
			<th>Nickname</th>
			<th>Email</th>
			<th>Status</th>
			<th>Update</th>
			<th>Disable</th>			
			<th>Delete</th>
		</tr>
		
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.nickname}</td>
				<td>${user.email}</td>
				<c:choose>
					<c:when test="${user.enabled}">
						<td>Active</td>
					</c:when>
					<c:otherwise>
						<td>Frozen</td>
					</c:otherwise>
				</c:choose>
				<td><button>Update</button></td>
				<td><button>Freeze</button></td>
				<td><button>Get rid of him!</button></td>
			</tr>
		</c:forEach>		
	</table>
	

	<script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/admin.js"/> "></script>
</body>
</html>