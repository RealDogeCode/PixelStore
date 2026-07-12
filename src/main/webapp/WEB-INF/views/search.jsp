<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="Models.Videogame" %>
<%@ page import="Models.Tag" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>PixelStore | Search</title>
	<script src="${pageContext.request.contextPath}/scripts/carousel.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
	<script src="${pageContext.request.contextPath}/scripts/ajaxSearch.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div id="games" class="grid padded">

<%
List<Videogame> games = (List<Videogame>) request.getAttribute("games");
if (games != null) {

    for (Videogame game : games) {

        request.setAttribute("game", game);
%>

        <jsp:include page="/WEB-INF/components/card.jsp"/>

<%
    }

}
%>
</div>

</body>
</html>