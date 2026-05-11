<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<ul>
				<li><a href=".">First Method</a></li>
				<li><a href=".">Second Method</a></li>
				<li><a href=".">Third Method</a></li>
				<li><a href=".">Fourth Method</a></li>
			</ul>
		<input type="button" value="Add Payment Method">
		</div>
	</div>
</body>
</html>