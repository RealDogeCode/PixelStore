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
				<li>Ordine 1<input type="button" value="Rimborsa"></li>
				<li>Ordine 2<input type="button" value="Rimborsa"></li>
				<li>Ordine 3<input type="button" value="Rimborsa"></li>
				<li>Ordine 4<input type="button" value="Rimborsa"></li>
			</ul>
		</div>
	</div>
</body>
</html>