<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String title = request.getParameter("title");
String description = request.getParameter("description");
%>
<div class="card">
<%= title %>
<br>
<%= description %>
</div>