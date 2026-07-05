<%@ page import="Models.User" %>
<%@ page import="Models.Role" %>

<nav class="hnav">
	<%
	User user = (User) session.getAttribute("user");
	String ctx = request.getContextPath();
	
	String link = (user != null) ? "/Account?section=info" : "/Login";
	String avatar = (user != null) ? user.getAvatarUrl() : "/images/avatars/default_profile_picture.jpg";
	
	String current = request.getRequestURI();
	%>
	<ul>
		<li class="nav-logo"><a href="${pageContext.request.contextPath}/Homepage"><img src="${pageContext.request.contextPath}/images/logo.png" width="50" height="50"></img></a></li>
		<li class="tabs">
			<ul>
				<li class="<%= current.contains("homepage") ? "active" : "" %>"><a href="${pageContext.request.contextPath}/Homepage">Homepage</a></li>
				
				<% if(user != null && user.getRoles().contains(Role.ADMIN)) { %>
				<li class="<%= current.contains("admin") ? "active" : "" %>"><a href="${pageContext.request.contextPath}/Admin/Dashboard">Admin Dashboard</a></li>
				<% } %>
				
				<% if(user != null && user.getRoles().contains(Role.PUBLISHER)) { %>
				<li class="<%= current.contains("publisher") ? "active" : "" %>"><a href="${pageContext.request.contextPath}/Publisher/Dashboard">Publisher Dashboard</a></li>
				<% } %>
			</ul>
		</li>
		<li class="nav-center nav-search"><input type="text" placeholder="Esplora il catalogo..."></li>
		<li class="nav-cart"><a href="${pageContext.request.contextPath}/Cart"><img src="${pageContext.request.contextPath}/images/cart.png"  width="50" height="50"></a></li>
		<li class="nav-user">${user.username}<a href="<%= ctx + link %>"><img src="<%= ctx + avatar %>" width="50" height="50"></a></li>
	</ul>
</nav>