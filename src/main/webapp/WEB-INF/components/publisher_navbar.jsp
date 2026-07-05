<%@ page import="Models.User" %>
<%@ page import="Models.Role" %>

<nav class="sidebar">
<ul>
    <li><a href="${pageContext.request.contextPath}/Publisher/Dashboard?section=add_game">Add Game</a></li>
    <li><a href="${pageContext.request.contextPath}/Publisher/Dashboard?section=modify_game">Modify Game</a></li>
</ul>
</nav>