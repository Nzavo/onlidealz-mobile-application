package com.example.OnliDealz.Black;

public class CartMod {
    //reg,serial,customer,product,details,price,quantity,cost,image,status,reg_date
    String reg = null;
    String customer = null;
    String product = null;
    String details = null;
    String price = null;
    String quantity = null;
    String cost = null;
    String image = null;
    String status = null;
    String reg_date = null;
    String total = null;
    String sum_quant = null;

    public CartMod(String reg, String customer, String product, String details, String price, String quantity, String cost, String image, String status, String reg_date, String total, String sum_quant) {
        this.reg = reg;
        this.customer = customer;
        this.product = product;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.cost = cost;
        this.image = image;
        this.status = status;
        this.reg_date = reg_date;
        this.total = total;
        this.sum_quant = sum_quant;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSum_quant() {
        return sum_quant;
    }

    public void setSum_quant(String sum_quant) {
        this.sum_quant = sum_quant;
    }

    @Override
    public String toString() {
        return price + " " + product + " " + details + " " + reg + " " + reg_date + " " + status + " " + quantity;

    }
}
