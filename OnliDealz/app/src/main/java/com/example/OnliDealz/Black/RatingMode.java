package com.example.OnliDealz.Black;

public class RatingMode {
    //id,customer,phone,fullname,message,rate,reg_date
    String id = null;
    String customer = null;
    String phone = null;
    String fullname = null;
    String message = null;
    String rate = null;
    String reg_date = null;

    public RatingMode(String id, String customer, String phone, String fullname, String message, String rate, String reg_date) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.fullname = fullname;
        this.message = message;
        this.rate = rate;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
    //id,customer,phone,fullname,message,rate,reg_date
    public String toString() {
        return customer + " " + id + " " + rate + " " + fullname + " " + message + " " + customer + " " + phone + " " + reg_date;
    }
}
