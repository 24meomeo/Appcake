package com.example.cherrycake.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VoucherModel implements Parcelable {

    String anh;
    String description;
    String title;
    int code;

    public VoucherModel(String anh, String description, String title, int code) {
        this.anh = anh;
        this.description = description;
        this.title = title;
        this.code = code;
    }

    protected VoucherModel(Parcel in) {
        anh = in.readString();
        description = in.readString();
        title = in.readString();
        code = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(anh);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeInt(code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoucherModel> CREATOR = new Creator<VoucherModel>() {
        @Override
        public VoucherModel createFromParcel(Parcel in) {
            return new VoucherModel(in);
        }

        @Override
        public VoucherModel[] newArray(int size) {
            return new VoucherModel[size];
        }
    };

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VoucherModel() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}