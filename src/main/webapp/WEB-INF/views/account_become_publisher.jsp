<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>PixelStore | Account - Become Publisher</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
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
				<form method="POST"
				      class="form-card"
				      action="Account?section=publisher"
				      enctype="multipart/form-data"
				      onsubmit="return validateForm(this);" 
				      novalidate>
				
				    <label for="name">Company Name:</label>
				    <input type="text"
				           name="name"
				           id="name"
				           required
				           minlength="3"
				           maxlength="50"
				           onchange="validateFormElem(this, document.getElementById('nameError'))">
				    <div id="nameError"></div>
				
				
				    <label for="desc">Short Description:</label>
				    <textarea id="desc"
				              name="desc"
				              rows="5"
				              cols="20"
				              required
				              minlength="10"
				              maxlength="500"
				              onchange="validateFormElem(this, document.getElementById('descError'))"></textarea>
				    <div id="descError"></div>
				
				
				    <label for="avatar-wrapper">Company Logo:</label>
				
				    <div class="avatar-layout">
				        <label class="avatar-wrapper">
				            <img class="avatar-upload"
				                 src="<%= request.getContextPath() %>/images/avatars/default_profile_picture.jpg"
				                 alt="Company Logo">
				
				            <div class="avatar-overlay">Change Logo</div>
				
				            <input type="file"
				                   name="logo"
				                   accept="image/*"
				                   required
				                   hidden
				                   onchange="validateFormElem(this, document.getElementById('logoError'))">
				        </label>
				
				        <div id="logoError"></div>
				    </div>
				
				
				    <input type="submit" value="Become Publisher">
				</form>
			</div>
		</div>
	</body>
</html>