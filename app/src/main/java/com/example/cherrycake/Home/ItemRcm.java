package com.example.cherrycake.Home;

public class ItemRcm {
    private String itrcmpict;
    private String itrcmname;
    private String itrcmprice;

    public ItemRcm(){}
    public ItemRcm(String itrcmpict, String itrcmname, String itrcmprice) {
        this.itrcmpict = itrcmpict;
        this.itrcmname = itrcmname;
        this.itrcmprice = itrcmprice;
    }

    public String getItrcmpict() {
        return itrcmpict;
    }

    public void setItrcmpict(String itrcmpict) {
        this.itrcmpict = itrcmpict;
    }

    public String getItrcmname() {
        return itrcmname;
    }

    public void setItrcmname(String itrcmname) {
        this.itrcmname = itrcmname;
    }

    public String getItrcmprice() {
        return itrcmprice;
    }

    public void setItrcmprice(String itrcmprice) {
        this.itrcmprice = itrcmprice;
    }
}
