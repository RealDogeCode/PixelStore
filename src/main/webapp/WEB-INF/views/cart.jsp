<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="Models.Videogame" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Carrello</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<%
String status = request.getParameter("status");
String error = (String) request.getAttribute("error");

if (status != null || error != null) {
%>
    <jsp:include page="/WEB-INF/components/modal.jsp" />
<%
}
%>

<%
    List<Videogame> cart = (List<Videogame>) request.getAttribute("cart");

    double total = 0;
    if (cart != null) {
        for (Videogame g : cart) {
            total += g.getPrice();
        }
    }
%>

<div class="padded">
    <h1 style="margin-bottom: 1rem; color:#2a2a4a;">Il tuo carrello</h1>
    <%
        if (cart == null || cart.isEmpty()) {
    %>
        <div class="alert alert-info">
            <p>Il carrello è vuoto.</p>
        </div>
    <%
        } else {
    %>

    <ul class="list">
        <%
            for (Videogame game : cart) {
        %>
        <li>
            <div style="display:flex; flex-direction:column; gap:4px;">
                <strong><%= game.getTitle() %></strong>

                <span style="font-size:0.85rem; color:#6b7280;">
                    <%= game.getPublisherName() %>
                </span>

                <span style="font-size:0.85rem;">
                    € <%= game.getPrice() %>
                </span>
            </div>
			<a href="Cart?remove_id=<%= game.getId() %>" class="btn">Rimuovi</a>
        </li>
        <%
            }
        %>
    </ul>
    <%
        }
    %>
    <div style="margin-top: 2rem; display:flex; justify-content:space-between; align-items:center;">
        <div style="font-size: 1.2rem; font-weight: 600; color:#2a2a4a;">
            Totale: € <%= String.format("%.2f", total) %>
        </div>

		<a href="Checkout" class="btn">Procedi con il pagamento.</a>
    </div>

</div>

</body>
</html>