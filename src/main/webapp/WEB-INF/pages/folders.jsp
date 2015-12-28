<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Folders</title>
	</head>
<body>

<!-- <sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="logoutUrl" value="/j_spring_security_logout"/>
		
		<form id="logoutForm" action="${logoutUrl}">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		
		<script>
			function logoutForm() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
		<c:if test="${pageContext.request.userPrincipal.name != null }">
			<h3>Hello ${pageContext.request.userPrincipal.name}!</h3>
			<button id="logoutButton" onclick="logoutForm()">Logout</button>
		</c:if>
		
	</sec:authorize> -->
	
	<c:url value="/folders/add" var="addFolderUrl"/>
	<c:url value="/folders/delete" var="deleteFolderUrl"/>
	
	<form name="additionForm" action="${addFolderUrl}" method="post">
		<input type="text" name="name"/>
		<input type="submit" value="Add New Folder"/>
	</form>
	
	<table>
		<th>Folder Name</th>
		<th></th>
		<th></th>
		
		<c:forEach var="f" items="${folders}">
			<tr>
				<td><a href="<c:url value="/bookmarks?id=${f.folderId}" />">${f.name}</a></td>
				<td><button id="folderEdit">Edit</button></td>				
				<td>
					<form action="${deleteFolderUrl}" method="post">
						<input type="hidden" name="id" value="${f.folderId}"/>
						<input type="submit" value="Delete"/>
					</form>
				</td>
			</tr>			
		</c:forEach>	
	</table>

</body>
</html>