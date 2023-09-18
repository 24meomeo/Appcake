package com.example.cherrycake.GioHang;

import android.os.Parcel;
import android.os.Parcelable;

public class MyCartModel implements Parcelable {

    public String anh;
    public String name;
    public int price;
    public int totalQuantity;
    public String id;


    public MyCartModel() {
    }

    public MyCartModel(String anh, String name, int price, int totalQuantity) {
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
    }

    protected MyCartModel(Parcel in) {
        anh = in.readString();
        name = in.readString();
        price = in.readInt();
        totalQuantity = in.readInt();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(anh);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(totalQuantity);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MyCartModel> CREATOR = new Parcelable.Creator<MyCartModel>() {
        @Override
        public MyCartModel createFromParcel(Parcel in) {
            return new MyCartModel(in);
        }

        @Override
        public MyCartModel[] newArray(int size) {
            return new MyCartModel[size];
        }
    };



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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
