package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartModel {

    private List<Videogame> items = new ArrayList<>();

    private Map<Integer, Integer> quantities = new HashMap<>();


    public List<Videogame> getItems() {
        return items;
    }


    public int getQuantity(int gameId) {
        return quantities.getOrDefault(gameId, 1);
    }


    public void addItem(Videogame game) {

        if (game == null) {
            return;
        }

        if (!items.contains(game)) {
            items.add(game);
            quantities.put(game.getId(), 1);
        } else {
            quantities.put(
                game.getId(),
                getQuantity(game.getId()) + 1
            );
        }
    }


    public void updateQuantity(int id, int quantity) {

        if (quantity <= 0) {
            removeItem(id);
            return;
        }

        quantities.put(id, quantity);
    }


    public void removeItem(int id) {

        items.removeIf(g -> g.getId() == id);
        quantities.remove(id);
    }


    public boolean isEmpty() {
        return items.isEmpty();
    }


    public double getTotal() {

        double total = 0;

        for (Videogame game : items) {

            int quantity = getQuantity(game.getId());

            total += game.getDiscountedPrice() * quantity;
        }

        return Math.round(total * 100.0) / 100.0;
    }
}