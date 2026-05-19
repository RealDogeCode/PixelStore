<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="centered">
	<form action="Login" method="POST">
		<h1 id="login-title">Login</h1>
		<label for="email">email</label>
		<input type="email" id="email" placeholder="email" name="email">
		<label for="password">password</label>
		<input type="password" id="password" placeholder="password" name="password">
		<%
		String error = (String) request.getAttribute("error");
		if (error != null) {
		%>
		    <div class="alert alert-danger"><%= error %></div>
		<%
		}
		%>
		<input type="submit" value="login">
	</form>
</div>
</body>
</html>