package com.example.cherrycake.data.model;

import java.io.Serializable;

public class Cake implements Serializable {
    private int radius;
    private int color;
    private int image;

    public Cake() {
    }

    public Cake(int radius, int color, int image) {
        this.radius = radius;
        this.color = color;
        this.image = image;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
