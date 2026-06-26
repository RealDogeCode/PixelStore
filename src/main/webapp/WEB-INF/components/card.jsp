<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="Models.Videogame" %>
<%@ page import="Models.Tag" %>

<%
Videogame game = (Videogame) request.getAttribute("game");
%>

<div class="card">
    <div class="card_image-wrapper">
        <img class="card_image" src="<%= game.getBannerUrl() %>">
    </div>
    <div class="card_body">
        <h3 class="card_title"><%= game.getTitle() %></h3>
        <div class="meta">
            <span class="author"><%= game.getPublisherName() %></span>
        </div>
        <div class="tags">
            <%
                if (game.getTags() != null) {
                    for (Tag tag : game.getTags()) {
            %>
                <span class="tag"><%= tag.getName() %></span>
            <%
                    }
                }
            %>
        </div>
    </div>
    <div class="card_footer">
        <span class="price">€ <%= game.getPrice() %></span>
        <a class="btn" href=".">Aggiungi al carrello</a>
    </div>
</div>