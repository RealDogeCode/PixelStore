<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Homepage</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<div class="carousel">
	<ul class="carousel-track">
	  <li class="carousel-item">
	    <div class="divided">
	    	<img alt="" src="https://i.postimg.cc/RCDdyxxq/banner.png">
			<div class="content">
			  <span class="tag">RPG</span>
			  <h1>Baldur's Gate III</h1>
			  <p>Turn-based fantasy adventure</p>
  			  <a href="." class="btn">Esplora</a>
			</div>	    </div>
	  </li>
	  <li class="carousel-item">
   	    <div class="divided">
	    	<img alt="" src="https://i.postimg.cc/RCDdyxxq/banner.png">
			<div class="content">
			  <span class="tag">RPG</span>
			  <h1>Baldur's Gate III</h1>
			  <p>Turn-based fantasy adventure</p>
  			  <a href="." class="btn">Esplora</a>
			</div>	    </div>
	  </li>
	  <li class="carousel-item">
  	    <div class="divided">
	    	<img alt="" src="https://i.postimg.cc/RCDdyxxq/banner.png">
			<div class="content">
			  <span class="tag">RPG</span>
			  <h1>Baldur's Gate III</h1>
			  <p>Turn-based fantasy adventure</p>
			  <a href="." class="btn">Esplora</a>
			</div>
	    </div>
	  </li>
	  <li  class="carousel-item">
   	    <div class="divided">
	    	<img alt="" src="https://i.postimg.cc/RCDdyxxq/banner.png">
			<div class="content">
			  <span class="tag">RPG</span>
			  <h1>Baldur's Gate III</h1>
			  <p>Turn-based fantasy adventure</p>
  			  <a href="." class="btn">Esplora</a>
			</div>
	    </div>
	  </li>
	</ul>
</div>

<%
List<String> tags = new ArrayList<>();
tags.add("RPG");
tags.add("Adventure");
tags.add("Online");

request.setAttribute("tags", tags);
%>
<div class="padded flex">
	<% for(int i = 0; i < 72; i++) { %>
	<% String title = "Game " + i; %>
	<jsp:include page="/WEB-INF/components/card.jsp">
	    <jsp:param name="title" value="<%=title%>"/>
	</jsp:include>
	<% } %>
</div>
</body>
</html>