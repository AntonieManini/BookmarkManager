<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<title>Folders</title>
	</head>
<body>

	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="logoutUrl" value="/logout"/>
		
		<form id="logoutForm" action="${logoutUrl}" method="post">
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
		
	</sec:authorize>
	
	<c:url value="/folders/add" var="addFolderUrl"/>
	<c:url value="/folders/delete" var="deleteFolderUrl"/>
	<c:url value="/folders/export" var="exportUrl"/>
	<c:url value="/folders/import" var="importUrl"/>
	
	<form name="exportForm" action="${exportUrl}" method="get">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="Export"/>
	</form>
	
	<form name="importForm" action="${importUrl}" method="post" enctype="multipart/form-data">
		<input type="file" name="file"/>
		<input type="submit" value="Import"/>
	</form>	
	
	<form name="additionForm" action="${addFolderUrl}" method="post">
		<input type="text" name="name"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="Add New Folder"/>
	</form>
	
	<table>
		<th></th>
		<th>Folder Name</th>
		<th></th>
		<th></th>
		
		<c:forEach var="f" items="${folders}">
			<tr>
				<td><input class="folder_id" type="hidden" value="${f.folderId}"/></td>
				<td><a class="folder_name" href="<c:url value="/bookmarks?id=${f.folderId}" />">${f.name}</a></td>
				<td><button class="editButton">Edit</button></td>				
				<td>
					<form action="${deleteFolderUrl}" method="post">
						<input type="hidden" name="id" value="${f.folderId}"/>
						<input type="submit" value="Delete"/>
					</form>
				</td>
			</tr>			
		</c:forEach>	
	</table>
	
	<div id="csrf">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</div>

	<script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/folders.js"/>"></script>
</body>
</html>