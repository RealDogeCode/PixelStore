package Models;

public class OrderItem {
    private int id;
    private int orderId;

    private int videogameId;
    private String title;
    private String publisher;
    private double price;

    private int quantity;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getVideogameId() { return videogameId; }
    public void setVideogameId(int videogameId) { this.videogameId = videogameId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}