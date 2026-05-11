<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Models.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="account-layout">
<jsp:include page="/WEB-INF/components/account_navbar.jsp" />
	<div class="account-content">
	    <form action="Account" method="POST" enctype="multipart/form-data">
	        <label for="username">Username</label>
	        <input type="text"
	               id="username"
	               placeholder="username"
	               value="${user.username}"
	               name="username">
	        <label for="email">Email</label>
	        <input type="email"
	               id="email"
	               placeholder="example@example.com"
	               value="${user.email}"
	               name="email">
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