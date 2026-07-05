<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Register</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<div class="centered">
	<form class="form-card" action="Register" method="POST">
		<h1 id="form-title">Registrati</h1>
		<label for="username">username</label>
		<input type="text" id="username" placeholder="username" name="username">
		<label for="email">email</label>
		<input type="email" id="email" placeholder="email" name="email">
		<label for="password">password</label>
		<input type="password" id="password" placeholder="password" name="password">
		<p>Hai un account? <a href="Login">Accedi</a>!</p>
		<input type="submit" value="Register">
	</form>
</div>
<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
    <%= error %>
<%
}
%>
</body>
</html>