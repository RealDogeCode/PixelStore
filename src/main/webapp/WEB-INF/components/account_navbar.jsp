<%@ page import="Models.User" %>
<%@ page import="Models.Role" %>

<nav class="sidebar">
<ul>
    <li><a href="${pageContext.request.contextPath}/Account?section=info">Account Information</a></li>
    <li><a href="${pageContext.request.contextPath}/Account?section=payment">Payment Method</a></li>
    <li><a href="${pageContext.request.contextPath}/Account?section=orders">I miei ordini</a></li>
    <%
    User user = (User) session.getAttribute("user");
    if(!user.getRoles().contains(Role.PUBLISHER)) { 
    %>
    <li><a href="${pageContext.request.contextPath}/Account?section=publisher">Diventa Publisher</a></li>
    <% } %>
    <li><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>
</ul>
</nav>