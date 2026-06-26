<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Account</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
	<body>	
		<jsp:include page="/WEB-INF/components/navbar.jsp" />
		
			<%
			String status = request.getParameter("status");
			String error = (String) request.getAttribute("error");
			
			if (status != null || error != null) {
			%>
			    <jsp:include page="/WEB-INF/components/modal.jsp" />
			<%
			}
			%>

			<div class="layout">
				<jsp:include page="/WEB-INF/components/account_navbar.jsp" />
				
				<div class="content">
				
				<form method="POST" action="Account?section=publisher" enctype="multipart/form-data">
				    <label for="name">Company Name:</label>
				    <input type="text" name="name" id="name" required>
				
				    <label for="desc">Short Description:</label>
				    <textarea id="desc" name="desc" rows="5" cols="20"></textarea>
				
					<label for="avatar-wrapper">Company Logo:</label>
					<div class="avatar-layout">
					    <label class="avatar-wrapper">
					        <img class="avatar-upload" src="<%= request.getContextPath()  %>/images/avatars/default_profile_picture.jpg" alt="Avatar utente">
					        <div class="avatar-overlay">Change Logo</div>
					        <input type="file" name="logo" hidden>
					    </label>
					
					</div>
				
				    <input type="submit" value="Become Publisher">
				</form>
			</div>
		</div>
	</body>
</html>