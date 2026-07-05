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
	<title>Homepage</title>
	<script src="${pageContext.request.contextPath}/scripts/carousel.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<%
	List<Videogame> games = (List<Videogame>) request.getAttribute("games");
	List<Videogame> featured = List.of();
	
	if (games != null && !games.isEmpty()) {
		Random random = new Random();

		List<Videogame> copy = new ArrayList<>(games);
		Collections.shuffle(copy);

		featured = new ArrayList<>(copy.subList(0, Math.min(4, copy.size())));
	}
%>

<div class="carousel">
    <ul class="carousel-track">
        <% for (Videogame game : featured) { %>
            <li class="carousel-item">
                <div class="slide">
                    <img src="<%= game.getBannerUrl() != null ? game.getBannerUrl() : "https://i.postimg.cc/RCDdyxxq/banner.png" %>" alt="<%= game.getTitle() %>">
                    <div class="content">
					<div class="tags">
					    <%
					        if (game.getTags() != null && !game.getTags().isEmpty()) {
					            for (Tag tag : game.getTags()) {
					    %>
					                <span class="tag"><%= tag.getName() %></span>
					    <%
					            }
					        } else {
					    %>
					            <span class="tag">Game</span>
					    <%
					        }
					    %>
					</div>
                        <h1><%= game.getTitle() %></h1>
                        
                        <div class="meta">
						    <span class="author">
						        <%= game.getPublisherName() %>
						    </span>
						</div>
                        
                        <p>
                            <%= game.getDescription() != null ? game.getDescription() : "No description available." %>
                        </p>
                        <a href="${pageContext.request.contextPath}/Cart?add_id=<%= game.getId() %>" class="btn">
                            € <%= game.getPrice() %>
                        </a>
                    </div>
                </div>
            </li>
        <% } %>
    </ul>
</div>

<%
List<Videogame> g = (List<Videogame>) request.getAttribute("games");
%>

<div class="padded grid">
<%
if (games != null) {
    for (Videogame game : g) {

        request.setAttribute("game", game);
%>

    <jsp:include page="/WEB-INF/components/card.jsp" />

<%
    }
}
%>
</div>
</body>
</html>