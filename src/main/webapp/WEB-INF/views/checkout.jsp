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
    <title>PixelStore | Checkout</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
    <script src="${pageContext.request.contextPath}/scripts/validation.js"></script>
</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<%
    CartModel cartObj = (CartModel) session.getAttribute("cart");
%>

<div class="padded">
    <h1>Checkout</h1>

	<div class="eqdivided">
    <ul class="list" style="margin-bottom: 2rem;">
        <%
            for (Videogame game : cartObj.getItems()) {
        %>
        <li>
            <span><%= game.getTitle() %> ✖ <%= cartObj.getQuantity(game.getId()) %></span>
            <span>€ <%= game.getDiscountedPrice() %></span>
        </li>
        <%
            }
        %>
    </ul>

	<footer class="cart-footer">
	    <div class="cart-total">
	        Totale:
	        € <%= cartObj.getTotal() %>
	    </div>
	</footer>

<form method="post"
      action="Checkout"
      class="form-card"
      onsubmit="return this.reportValidity();">
    <label>Nome intestatario</label>
    <input type="text"
           name="cardName"
           required
           minlength="3"
           maxlength="50"
           onchange="validateFormElem(this, document.getElementById('cardNameError'))">
    <span id="cardNameError"></span>
    <label>Numero carta</label>
    <input type="text"
           name="cardNumber"
           required
           minlength="16"
           maxlength="16"
           pattern="[0-9]{16}"
           inputmode="numeric"
           onchange="validateFormElem(this, document.getElementById('cardNumberError'))">
    <span id="cardNumberError"></span>
    <label>Scadenza</label>
    <input type="text"
           name="expiry"
           placeholder="MM/YY"
           required
           pattern="(0[1-9]|1[0-2])\/[0-9]{2}"
           onchange="validateFormElem(this, document.getElementById('expiryError'))">
    <span id="expiryError"></span>
    <label>CVV</label>
    <input type="password"
           name="cvv"
           required
           minlength="3"
           maxlength="3"
           pattern="[0-9]{3}"
           inputmode="numeric"
           onchange="validateFormElem(this, document.getElementById('cvvError'))">
    <span id="cvvError"></span>
    <input type="hidden"
           name="total"
           value="<%= cartObj.getTotal() %>">
    <input type="submit"
           class="btn"
           value="Conferma pagamento">
</form>
</div>

</body>
</html>