<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<form action="Login" method="POST">
	<label for="email">email</label>
	<input type="email" id="email" placeholder="email" name="email">
	<label for="password">password</label>
	<input type="password" id="password" placeholder="password" name="password">
	<input type="submit" value="login">
</form>
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