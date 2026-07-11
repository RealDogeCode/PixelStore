<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>PixelStore | Register</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<div class="centered">
	<form class="auth-card"
	      action="Register"
	      method="POST"
	      onsubmit="return this.reportValidity();">
	
	    <h1 id="form-title">Registrati</h1>
	
	    <label for="username">Username</label>
	    <input type="text"
	           id="username"
	           placeholder="username"
	           name="username"
	           required
	           minlength="3"
	           maxlength="20"
	           onchange="validateFormElem(this, document.getElementById('usernameError'))">
	    <div id="usernameError"></div>
	
	
	    <label for="email">Email</label>
	    <input type="email"
	           id="email"
	           placeholder="email"
	           name="email"
	           required
	           onchange="validateFormElem(this, document.getElementById('emailError'))">
	    <div id="emailError"></div>
	
	
	    <label for="password">Password</label>
	    <input type="password"
	           id="password"
	           placeholder="password"
	           name="password"
	           required
	           minlength="5"
	           onchange="validateFormElem(this, document.getElementById('passwordError'))">
	    <div id="passwordError"></div>
	
	
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