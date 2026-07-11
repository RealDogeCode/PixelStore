<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>PixelStore | Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="centered">
    <form class="auth-card" action="Login" method="POST">
        <h1 class="title">Accedi</h1>

        <label for="email">Email</label>
        <input
            type="email"
            id="email"
            name="email"
            placeholder="Email"
            required
            onchange="validateFormElem(this, document.getElementById('emailError'))">
        <span id="emailError"></span>

        <label for="password">Password</label>
        <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            required
            minlength="5"
            onchange="validateFormElem(this, document.getElementById('passwordError'))">
		<div class="checkbox-group">
		    <input type="checkbox"
		           id="keeplogged"
		           name="keeplogged"
		           value="true">
		
		    <label for="keeplogged">
		        Rimani autenticato (30 Giorni)
		    </label>
		</div>
        <span id="passwordError"></span>

        <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
        }
        %>

        <p>Non hai un account? <a href="Register">Registrati</a>!</p>

        <input type="submit" value="Login">
    </form>
</div>
</body>
</html>