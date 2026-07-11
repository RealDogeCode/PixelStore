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
    <title>PixelStore | Cart</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<%
String status = request.getParameter("status");
String error = (String) request.getAttribute("error");

if (status != null || error != null) {
%>
    <jsp:include page="/WEB-INF/components/modal.jsp"/>
<%
}

CartModel cart = (CartModel) session.getAttribute("cart");
%>

<div class="cart-page">
    <div class="content">
        <h1>Il tuo carrello</h1>

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
            for (Videogame game : cart.getItems()) {
            %>
                <li class="cart-item">
                    <div class="cart-info">
                        <strong class="cart-title">
                            <%= game.getTitle() %>
                        </strong>

                        <span class="cart-publisher">
                            <%= game.getPublisherName() %>
                        </span>

                        <span class="price">
                        <%
                        if (game.getDiscountPercentage() > 0) {
                        %>
                            <del>
                                € <%= game.getPrice() %>
                            </del>
                            € <%= game.getDiscountedPrice() %>
                        <%
                        } else {
                        %>
                            € <%= game.getPrice() %>
                        <%
                        }
                        %>
                        </span>
                    </div>

					<div class="quantity-control">
					
					    <a href="Cart?decrease_id=<%= game.getId() %>" class="quantity-btn">
					        −
					    </a>
					
					    <span class="quantity-value">
					        <%= cart.getQuantity(game.getId()) %>
					    </span>
					
					    <a href="Cart?increase_id=<%= game.getId() %>" class="quantity-btn">
					        +
					    </a>
					
					</div>
                </li>
            <%
            }
            %>
            </ul>
        <%
        }
        %>
    </div>
</div>


<footer class="cart-footer">
    <div class="cart-total">
        Totale:
        € <%= String.format("%.2f", cart.getTotal()) %>
    </div>

    <a href="Checkout" class="btn">
        Procedi con il pagamento
    </a>
</footer>

</body>
</html>