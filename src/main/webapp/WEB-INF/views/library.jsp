<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Library</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<ul>
<li>Game 1 <input type="button" value="Download"></li>
<li>Game 2 <input type="button" value="Download"></li>
<li>Game 3 <input type="button" value="Download"></li>
<li>Game 4 <input type="button" value="Download"></li>
</ul>
</body>
</html>