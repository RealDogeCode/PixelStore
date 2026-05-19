<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Account</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	</head>
<body>
	<jsp:include page="/WEB-INF/components/navbar.jsp" />
	<div class="account-layout">
	<jsp:include page="/WEB-INF/components/account_navbar.jsp" />
		<div class="account-content">
			<ul class="list">
				<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dictum ut lorem eget consectetur. Sed eget lacus nisl. Nam eget ante elementum.<a class="btn" href=".">Rimborsa</a></li>
				<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dictum ut lorem eget consectetur. Sed eget lacus nisl. Nam eget ante elementum.<a class="btn" href=".">Rimborsa</a></li>
				<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dictum ut lorem eget consectetur. Sed eget lacus nisl. Nam eget ante elementum.<a class="btn" href=".">Rimborsa</a></li>
				<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dictum ut lorem eget consectetur. Sed eget lacus nisl. Nam eget ante elementum.<a class="btn" href=".">Rimborsa</a></li>
			</ul>
		</div>
	</div>
</body>
</html>