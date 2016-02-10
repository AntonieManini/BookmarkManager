<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">	
	<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<div id="wrapper">
		<div class="header">
			<div id="user-info">
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
						<div class="authenticated-user">
<!--							<h3 class="authenticated-user">Hello ${pageContext.request.userPrincipal.name}!</h3>-->
							<p class="authenticated-user">Hello, ${nickname}!</p>
							<button class="authenticated-user" id="logoutButton" onclick="logoutForm()">Logout</button>
						</div>
					</c:if>
					
				</sec:authorize>
			</div>
			<div id="export-import">	
				<c:url value="/folders/add" var="addFolderUrl"/>	
				<c:url value="/folders/export" var="exportUrl"/>
				<c:url value="/folders/import" var="importUrl"/>

				<div class="header">
					<form name="exportForm" action="${exportUrl}" method="get">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="submit" value="Export"/>
					</form>
					
					<form:form name="importForm" action="${importUrl}" method="post" commandName="importOption" enctype="multipart/form-data">
						<input type="file" name="file"/>
						<form:select path="option">
							<form:option value="merge">Merge</form:option>
							<form:option value="overwrite">Overwrite</form:option>
						</form:select>
						<input type="submit" value="Import"/>
					</form:form>		
				</div>
			</div>
		</div>
		<div id="horizontal-line"></div>
		<div class="container">
		<div id="tree-pane" class="pane">
			<div class="tree_header">
				<div id="folderAddForm">
					<input class="newFolderName" type="text" name="name" required pattern="[a-zA-Z0-9]+"/>
					<input id="addFolderSubmit" type="submit" value="Add New Folder"/>
				</div>			
			</div>
			<div class="tree_content">
				<ul>
					<custom:folderTree list="${folders}"></custom:folderTree>
				</ul>			
			</div>
		</div>		
		<div id="list-pane" class="pane">			
			<div class="list_header">
				<form id="bookmarkAddForm">
					<input id="inputDesc" type="text" required/>
					<input id="inputUrl" type="url" required/>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Add New Bookmark"/>
				</form>			
			</div>
			<div class="list_content">
				<table>
				</table>
			</div>		
		</div>
	</div>
	</div>
	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/folders.js"/>"></script>
	<script src="<c:url value="/resources/js/bookmarks.js"/>"></script>
</body>
</html>
