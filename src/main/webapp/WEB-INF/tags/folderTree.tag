<%@ attribute name="list" required="true" type="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<c:url var="folderIcon" value="/resources/img/folder.png"/>
<c:forEach var="f" items="${list}">
	<li>
		<div class="content">
			<img src="${folderIcon}" style="width: 20px; height: 20px;"/>
			<input class="folder_id" type="hidden" value="${f.folderId}"/>
			<a class="folder_name" href="<c:url value="/bookmarks?id=${f.folderId}" />">${f.name}</a>
			<button class="editButton" style="display:none;"></button>
			<button class="deleteButton" style="display:none;"></button>				
			<button class="addSubFolderButton" style="display:none;"></button>
		</div>

		<c:if test="${not empty f.children}">			
	 		<ul>
				<custom:folderTree list="${f.children}"/>
			</ul>
		</c:if>
	</li>
</c:forEach>
 