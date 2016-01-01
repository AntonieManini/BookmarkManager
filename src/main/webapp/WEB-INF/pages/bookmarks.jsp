<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<title>Bookmarks</title>
</head>
<body>
	<c:url value="/bookmarks/add" var="addBookmarkUrl"/>
	<c:url value="/bookmarks/delete" var="deleteBookmarkUrl"/>
	
	<form:form name="bookmarkAddFrom" action="${addBookmarkUrl}" method="post" commandName="bookmarkForm">
		<input type="hidden" name="id" value="${folderId}"/>
		<form:input id="inputDesc" type="text" path="desc"/>
		<form:input id="inputUrl" type="text" path="url"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="Add New Bookmark"/>
	</form:form>


	<table>
		<th></th>
		<th>Description</th>
		<th>Link</th>
		<th>Edit</th>
		<th>Delete</th>
		
		<c:forEach var="b" items="${bookmarks}">
			<tr>
				<td><input class="bookmark_id" type="hidden" value="${b.bookmarkId}"/>
				<td><p class="bookmark_desc">${b.desc}</p></td>
				<td><a class="bookmark_url" href="${b.url}" target="_blank">Link</a></td>
				<td><button class="editButton">Edit</button></td>
				<td>
					<form action="${deleteBookmarkUrl}" method="post">
						<input type="hidden" name="id" value="${b.bookmarkId}"/>
						<input type="hidden" name="folderId" value="${folderId}"/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="submit" value="Delete"/>
					</form>
				</td>
			</tr>
		</c:forEach>	
	</table>
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bookmarks.js"/>"></script>	
</body>
</html>