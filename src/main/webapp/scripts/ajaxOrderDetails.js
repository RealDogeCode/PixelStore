window.loadOrderDetails = function(orderId) {
    var box = document.getElementById("details-" + orderId);
    if (!box) return;

	if(box.style.display == "block"){
		box.style.display = "none";
		return;
	}
	
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/PixelStore/OrderDetails?id=" + orderId, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);
            var html = "";

            if (!data || data.length === 0) {
                html = "Nessun dettaglio disponibile";
            } else {
                for (var i = 0; i < data.length; i++) {
                    html +=
                        "<div class='order-item'>" +
                        "<strong>" + data[i].title + "</strong><br>" +
                        "€ " + data[i].price + " × " + data[i].quantity +
                        "</div>";
                }
            }

            box.innerHTML = html;
            box.style.display = "block";
        }
    };

    xhr.send();
};