package com.example.cherrycake.QuanLy;

public class TaiKhoanModel {
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    String idUser;
    String Fullname;
    String Mail;
    String Phone;
    public TaiKhoanModel(String Mail) {
        this.Mail = Mail;
    }
    public TaiKhoanModel(){}
    public TaiKhoanModel(String Fullname, String Mail, String Phone) {
        this.Fullname = Fullname;
        this.Mail = Mail;
        this.Phone = Phone;
    }
    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        this.Fullname = fullname;
    }

    public String getMail() {
        return Mail;
    }
    public void setMail(String mail) {
        this.Mail = mail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }




}
