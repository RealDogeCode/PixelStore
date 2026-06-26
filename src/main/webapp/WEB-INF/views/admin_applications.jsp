<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Publisher" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Admin Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />
<div class="layout">
    <jsp:include page="/WEB-INF/components/admin_navbar.jsp" />
    <div class="content">
        <div class="requests-container">
            <div class="requests-header">
                <h1>Publisher Requests</h1>
                <p>Manage new publisher applications</p>
            </div>
            <div class="requests-list">
                <%
                    List<Publisher> applications = (List<Publisher>) request.getAttribute("applications");
                    if (applications != null) {
                        for (Publisher p : applications) {
                %>
                <div class="request-card">
                    <div class="request-info">
                    	<div class="nav-user"><img width="70" height="70" src="${request.getContextPath()}<%= p.getLogoUrl() %>"></img></div>
                        <h3><%= p.getCompanyName() %></h3>
                        <p>Description: <%= p.getDescription() %></p>
                        <p>Requested: <%= p.getCreatedAt() %></p>
                    </div>
                    <div class="request-actions">
                        <form method="post" action="<%= request.getContextPath() %>/Admin/Dashboard" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= p.getUserId() %>">
                            <button type="submit" name="action" value="approve" class="btn approve">Approve</button>
                        </form>
                        <form method="post" action="<%= request.getContextPath() %>/Admin/Dashboard" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= p.getUserId() %>">
                            <button type="submit" name="action" value="reject" class="btn reject">Reject</button>
                        </form>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
</div>
</body>
</html>