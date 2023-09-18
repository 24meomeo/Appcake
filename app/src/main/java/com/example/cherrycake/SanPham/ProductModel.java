package com.example.cherrycake.SanPham;

public class ProductModel {
    String user, name, description, category, image;
    int price;

    public ProductModel() {
    }

    public ProductModel(String user, String name, String description, String category, String image, int price) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) { this.user = user; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
