package com.example.cherrycake.QuanLy;

public class SanPhamModel {
    String image, name, category, description;
    int price, soluong;
    String id;



    public SanPhamModel() {}

    public SanPhamModel(String image, String name, String category, int price) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public SanPhamModel(String image, String name, String category, String description, int price, int soluong, String id) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.soluong = soluong;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
