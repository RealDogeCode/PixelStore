<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Models.User" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>PixelStore | Account - Info</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<%
String s = request.getParameter("status");
String e = (String) request.getAttribute("error");

if (s != null || e != null) {
%>
    <jsp:include page="/WEB-INF/components/modal.jsp" />
<%
}
%>
<div class="layout">
<jsp:include page="/WEB-INF/components/account_navbar.jsp" />
	<div class="content">
	    <form action="Account" class="form-card" method="POST" enctype="multipart/form-data" onsubmit="return this.reportValidity()">
		    <label for="username">Username</label>
		    <input type="text"
		           id="username"
		           placeholder="username"
		           value="${user.username}"
		           name="username"
		           required
		           minlength="3"
		           maxlength="20"
		           onchange="validateFormElem(this, document.getElementById('usernameError'))">
		    <div id="usernameError"></div>
		
		
		    <label for="email">Email</label>
		    <input type="email"
		           id="email"
		           placeholder="example@example.com"
		           value="${user.email}"
		           name="email"
		           required
		           onchange="validateFormElem(this, document.getElementById('emailError'))">
		    <div id="emailError"></div>
	        <%
	            User user = (User) session.getAttribute("user");
	            String ctx = request.getContextPath();
	            String avatar = (user != null && user.getAvatarUrl() != null)
	                    ? user.getAvatarUrl()
	                    : "/images/avatars/default_profile_picture.jpg";
	        %>
			<div class="avatar-layout">
			    <label class="avatar-wrapper">
			        <img class="avatar-upload" src="<%= ctx + avatar %>" alt="Avatar utente">
			        <div class="avatar-overlay">Change Avatar</div>
			        <input type="file" name="avatar" hidden>
			    </label>
			
			</div>
	        <input type="submit" value="Modifica">
	    </form>
	    <%
		String error = (String) request.getAttribute("error");
		if (error != null) {
		%>
		    <%= error %>
		<%
		}
		%>
	</div>
</div>

</body>
</html>