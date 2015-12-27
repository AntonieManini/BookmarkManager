<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Bookmarks</title>
</head>
<body>
	<table>
		<th>Description</th>
		<th>Link</th>
		
		<c:forEach var="b" items="${bookmarks}">
			<tr>
				<td>${b.desc}</td>
				<td><a href="${b.url}">Link</a></td>
			</tr>
		</c:forEach>	
	</table>
</body>
</html>