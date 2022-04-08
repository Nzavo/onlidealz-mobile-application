package com.example.OnliDealz.Black;

public class ReviewMode {
    //reg,quality,rate,price,value,product,customer,category,type,name,phone,status,reg_date
    String reg = null;
    String quality = null;
    String rate = null;
    String price = null;
    String value = null;
    String reference = null;
    String customer = null;
    String name = null;
    String phone = null;
    String status = null;
    String reg_date = null;

    public ReviewMode(String reg, String quality, String rate, String price, String value, String reference, String customer, String name, String phone, String status, String reg_date) {
        this.reg = reg;
        this.quality = quality;
        this.rate = rate;
        this.price = price;
        this.value = value;
        this.reference = reference;
        this.customer = customer;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.reg_date = reg_date;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return reg + " " + quality + " " + rate + " " + price + " " + value + " " + reference + " " + customer + " " + name + " " + phone + " " + status + " " + reg_date;
    }
}
