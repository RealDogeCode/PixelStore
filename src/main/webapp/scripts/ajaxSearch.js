function ajaxSearch(value) {
    const container = document.getElementById("games");

    const xhr = new XMLHttpRequest();

    xhr.open(
        "GET",
        "/PixelStore/SearchApi?q=" + value,
        true
    );

    xhr.onload = function() {
        if (xhr.status === 200 && xhr.readyState === 4) {
            const games = JSON.parse(xhr.responseText);

            container.innerHTML = "";

            games.forEach(game => {
                let tags = "";
                if (game.tags) {
                    game.tags.forEach(tag => {
                        tags += `
                            <span class="tag">
                                ${tag.name}
                            </span>
                        `;
                    });
                }

                let price;

                if (game.discountPercentage > 0) {
                    price = `
                        <del>
                            € ${game.price.toFixed(2)}
                        </del>

                        € ${game.discountedPrice.toFixed(2)}
                    `;
                } else {
                    price = `
                        € ${game.price.toFixed(2)}
                    `;
                }

                const card = document.createElement("div");

                card.className = "card";

                card.innerHTML = `
                    <div class="card_image-wrapper">
                        <img class="card_image"
                             src="${game.bannerUrl}">
                    </div>


                    <div class="card_body">

                        <h3 class="card_title">
                            ${game.title}
                        </h3>


                        <div class="meta">
                            <span class="author">
                                ${game.publisherName}
                            </span>
                        </div>


                        <div class="tags">
                            ${tags}
                        </div>

                    </div>


                    <div class="card_footer">

                        <span class="price">
                            ${price}
                        </span>


                        <a class="btn"
                           href="/PixelStore/Cart?add_id=${game.id}">
                            Aggiungi al carrello
                        </a>

                    </div>

                `;

                container.appendChild(card);
            });
        }
    };
    xhr.send();
}