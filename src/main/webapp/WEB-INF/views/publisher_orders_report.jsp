<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.OrdersReport" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>PixelStore | Publisher - Orders Report</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>

</head>

<body>

<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="layout">
    <jsp:include page="/WEB-INF/components/publisher_navbar.jsp" />

    <div class="content">
        <div class="orders-report">

            <h1>Orders Report</h1>
            <p>Visualizza il numero di ordini effettuati da ogni cliente in un intervallo di date.</p>

			<form class="report-form" method="get" action="<%= request.getContextPath() %>/Publisher/Dashboard" onsubmit="return this.reportValidity();">
			    <input type="hidden" name="section" value="orders_report">
			
			    <label>Data iniziale</label>
			    <input type="date"
			           id="from"
			           name="from"
			           required
			           onchange="validateFormElem(this, document.getElementById('fromError'))">
			    <span id="fromError"></span>
			
			    <label>Data finale</label>
			    <input type="date"
			           id="to"
			           name="to"
			           required
			           onchange="validateFormElem(this, document.getElementById('toError'))">
			    <span id="toError"></span>
			
			    <input type="submit" value="Visualizza report">
			</form>

            <%
            List<OrdersReport> report = (List<OrdersReport>) request.getAttribute("report");

            if (report != null) {
            %>

            <table class="orders-table">
                <thead>
                    <tr>
                        <th>Cliente</th>
                        <th>Numero ordini</th>
                    </tr>
                </thead>

                <tbody>
                <%
                if (report.isEmpty()) {
                %>
                    <tr>
                        <td colspan="2" class="empty-report">Nessun ordine trovato nel periodo selezionato.</td>
                    </tr>
                <%
                } else {
                    for (OrdersReport row : report) {
                %>
                    <tr>
                        <td><%= row.getCustomerName() %></td>
                        <td><%= row.getTotalOrders() %></td>
                    </tr>
                <%
                    }
                }
                %>
                </tbody>
            </table>

            <%
            }
            %>

        </div>
    </div>
</div>

</body>
</html>