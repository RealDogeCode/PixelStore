package Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Videogame {
    private int id;

    private String title;
    private String description;
    
    private int discountPercentage;

    private int publisherId;
    private String publisherName;

    private double price;

    private String bannerUrl;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    private List<Tag> tags = new ArrayList<>();

    public Videogame() {
    }

    public Videogame(int id) {
        this.id = id;
    }

    public Videogame(int id, String title, String description, String developer,
                     int publisherId, double price,
                     String bannerUrl,
                     Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publisherId = publisherId;
        this.price = price;
        this.bannerUrl = bannerUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountedPrice() {
        double discounted = price - (price * discountPercentage / 100.0);
        return Math.round(discounted * 100.0) / 100.0;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

	public void setPublisherName(String name) {
		this.publisherName = name;
	}

	public String getPublisherName() {
		return publisherName;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Videogame videogame = (Videogame) o;
        return id == videogame.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public void setDiscountPercentage(int int1) {
		discountPercentage = int1;
		
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}
}