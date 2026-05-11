<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Homepage</title>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<div class="carousel">
	<div id="slide-1">
	  First Slide
	</div>
	<div id="slide-2">
	  Second Slide
	</div>
	<div id="slide-3">
	  Third Slide
	</div>
	<div id="slide-4">
	  Fouth Slide
	</div>
	<div id="slide-5">
	  Fifth Slide
	</div>
</div>

<jsp:include page="/WEB-INF/components/card.jsp">
    <jsp:param name="title" value="card1"/>
    <jsp:param name="description" value="desc1"/>
</jsp:include>

</body>
</html>