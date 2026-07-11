<%
String error = (String) request.getAttribute("error");
String status = request.getParameter("status");

String type;
String message;

if (error != null) {
    type = "error";
    message = error;
} else if ("success".equals(status)) {
    type = "success";
    message = "Operazione completata con successo!";
} else {
    return;
}
%>
<script src="${pageContext.request.contextPath}/scripts/modal.js"></script>
<div id="modal-overlay" class="modal-overlay <%= type %>">
    <div class="modal-box <%= type %>">
        <p><%= message %></p>
        <button class="btn" onclick="closeModal()">OK</button>
    </div>
</div>