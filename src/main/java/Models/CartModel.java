package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartModel {

    private List<Videogame> items = new ArrayList<>();

    public List<Videogame> getItems() {
        return items;
    }

    public void addItem(Videogame game) {
        if (game != null && !items.contains(game)) {
            items.add(game);
        }
    }

    public void removeItem(int id) {
        items.removeIf(g -> g.getId() == id);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(Videogame::getPrice)
                .sum();
    }
}