<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Bookmarks</title>
</head>
<body>
	<c:url value="/bookmarks/add" var="addBookmarkUrl"/>
	<c:url value="/bookmarks/delete" var="deleteBookmarkUrl"/>
	
	<form:form name="bookmarkAddFrom" action="${addBookmarkUrl}" method="post" commandName="bookmarkForm">
		<input type="hidden" name="id" value="${folderId}"/>
		<form:input type="text" path="desc"/>
		<form:input type="text" path="url"/>
		<input type="submit" value="Add New Bookmark"/>
	</form:form>


	<table>
		<th>Description</th>
		<th>Link</th>
		<th>Edit</th>
		<th>Delete</th>
		
		<c:forEach var="b" items="${bookmarks}">
			<tr>
				<td>${b.desc}</td>
				<td><a href="${b.url}" target="_blank">Link</a></td>
				<td><a href="">Edit</a></td>
				<td>
					<form action="${deleteBookmarkUrl}" method="post">
						<input type="hidden" name="id" value="${b.bookmarkId}"/>
						<input type="hidden" name="folderId" value="${folderId}"/>
						<input type="submit" value="Delete"/>
					</form>
				</td>
			</tr>
		</c:forEach>	
	</table>
</body>
</html>