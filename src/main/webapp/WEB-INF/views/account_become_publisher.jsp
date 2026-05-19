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
				<form method="POST" action=".">
					<label for="name">Name:</label>
					<input type="text" id="name" placeholder="Publisher Name">
					
					<label for="email">Email:</label>
					<input type="email" id="email" placeholder="Publisher Email">
					
					<div class="radio-group">
					    <label>
					        <input type="radio" name="type" value="individual">
					        Individual
					    </label>
					
					    <label>
					        <input type="radio" name="type" value="company">
					        Company
					    </label>
					</div>
					
					<label for="desc">Short Description:</label>
					<textarea id="desc" rows=5 cols="20" placeholder="Description of your game publisher"></textarea>
					<input type="submit" value="Invia">
				</form>
			</div>
		</div>
	</body>
</html>