function editGame(id, title, description, price, discountPercentage, tags) {
    document.getElementById("gameId").value = id;
    document.getElementById("title").value = title;
    document.getElementById("description").value = description;
    document.getElementById("price").value = price;

    let slider = document.getElementById("discountPercentage");
    slider.value = discountPercentage;
    document.getElementById("discountValue").innerText = discountPercentage + "%";

    let select = document.getElementById("tagIds");

    for (let option of select.options) {
        option.selected = tags.includes(parseInt(option.value));
    }

    document.getElementById("hidden_form").classList.remove("hidden");
}