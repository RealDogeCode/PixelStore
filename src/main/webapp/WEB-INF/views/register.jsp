<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<form action="Register" method="POST">
	<label for="username">username</label>
	<input type="text" id="username" placeholder="username" name="username">
	<label for="email">email</label>
	<input type="email" id="email" placeholder="email" name="email">
	<label for="password">password</label>
	<input type="password" id="password" placeholder="password" name="password">
	<input type="submit" value="Register">
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