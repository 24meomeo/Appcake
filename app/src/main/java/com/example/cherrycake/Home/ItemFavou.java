package com.example.cherrycake.Home;

public class ItemFavou {
    private String itfvpict;
    private String itfvname;
    private String itfvprice;

    public ItemFavou(){}
    public ItemFavou(String itImage, String itname, String itprice) {
        this.itfvpict = itImage;
        this.itfvname = itname;
        this.itfvprice = itprice;
    }

    public String getItfvpict() {
        return itfvpict;
    }

    public void setItfvpict(String itfvpict) {
        this.itfvpict = itfvpict;
    }

    public String getItfvname() {
        return itfvname;
    }

    public void setItfvname(String itfvname) {
        this.itfvname = itfvname;
    }

    public String getItfvprice() {
        return itfvprice;
    }

    public void setItfvprice(String itfvprice) {
        this.itfvprice = itfvprice;
    }
}
