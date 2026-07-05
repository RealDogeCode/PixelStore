<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="Models.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Account - Ordini</title>

    <script src="${pageContext.request.contextPath}/scripts/ajaxOrderDetails.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/orders.css">
</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="layout">
    <jsp:include page="/WEB-INF/components/account_navbar.jsp" />
    <div class="content">
        <h2 class="orders-title">I tuoi ordini</h2>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
        %>
        <ul class="list">
        <%
            if (orders == null || orders.isEmpty()) {
        %>
            <li class="order-empty">Nessun ordine effettuato.</li>
        <%
            } else {
                for (Order o : orders) {
        %>
            <li class="order-item-row">
                <div class="order-header">
                    <div class="order-info">
                        <strong>Ordine #<%= o.getId() %></strong><br>
                        Totale: € <%= String.format("%.2f", o.getTotal()) %><br>
                        Data: <%= o.getCreatedAt() %>
                    </div>
                    <button class="btn"
                        onclick="loadOrderDetails(<%= o.getId() %>)">
                        Dettagli
                    </button>
                </div>
                <div id="details-<%= o.getId() %>" class="order-details hidden"></div>
            </li>
        <%
                }
            }
        %>
        </ul>
    </div>
</div>

</body>
</html>