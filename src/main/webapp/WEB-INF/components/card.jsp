<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
String title = request.getParameter("title");

List<String> tags = (List<String>) request.getAttribute("tags");

if (tags == null) {
    tags = new ArrayList<>();
}
%>

<div class="card">
	<div class="card_image-wrapper">
	<img class="card_image" src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/960px-Placeholder_view_vector.svg.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail">
    </div>
    <h3 class="card_title"><%= title %></h3>
    <% for(String name : tags) { %>
    <span class="tag"><%= name %></span>
	<% } %>
</div>