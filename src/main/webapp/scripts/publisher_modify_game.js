function editGame(id, title, description, price, tagIds) {
    document.getElementById("gameId").value = id;
    document.getElementById("title").value = title;
    document.getElementById("description").value = description;
    document.getElementById("price").value = price;

    const select = document.getElementById("tagIds");

    for (let opt of select.options) {
        opt.selected = tagIds.includes(parseInt(opt.value));
    }

    document.getElementById("hidden_form").classList.remove("hidden");
}