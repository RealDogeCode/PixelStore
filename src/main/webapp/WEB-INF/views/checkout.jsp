<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="Models.Videogame" %>
<%@ page import="Models.CartModel" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Checkout</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<%
    CartModel cartObj = (CartModel) session.getAttribute("cart");
    List<Videogame> cart = (cartObj != null) ? cartObj.getItems() : new ArrayList<>();

    double total = (cartObj != null) ? cartObj.getTotal() : 0;
%>

<div class="padded">
    <h1 style="margin-bottom: 1rem; color:#2a2a4a;">Checkout</h1>

    <ul class="list" style="margin-bottom: 2rem;">
        <%
            for (Videogame game : cart) {
        %>
        <li>
            <span><%= game.getTitle() %></span>
            <span>€ <%= game.getPrice() %></span>
        </li>
        <%
            }
        %>
    </ul>

    <div style="margin-bottom: 2rem; font-size: 1.2rem; font-weight: 600; color:#2a2a4a;">
        Totale: € <%= String.format("%.2f", total) %>
    </div>

    <form method="post" action="Checkout">
        <label>Nome intestatario</label>
        <input type="text" name="cardName" required>

        <label>Numero carta</label>
        <input type="text" name="cardNumber" required maxlength="16">

        <label>Scadenza</label>
        <input type="text" name="expiry" placeholder="MM/YY" required>

        <label>CVV</label>
        <input type="password" name="cvv" required maxlength="3">

        <input type="hidden" name="total" value="<%= total %>">

        <input type="submit" class="btn" value="Conferma pagamento">

    </form>
</div>

</body>
</html>