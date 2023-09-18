package com.example.cherrycake.GioHang.ThanhToan;

public class ThanhToanModel {
    String anh;
    String name;
    int price;
    int totalQuantity;
//    int TrangThai = 1;
//
//    public int getTrangThai() {
//        return TrangThai;
//    }
//
//    public void setTrangThai(int trangThai) {
//        TrangThai = trangThai;
//    }



    String currentDate;
    String currentTime;

    public ThanhToanModel(String anh, String name, int price, int totalQuantity) {
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
    }

    public ThanhToanModel() {
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
