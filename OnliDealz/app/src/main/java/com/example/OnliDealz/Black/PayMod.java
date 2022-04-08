package com.example.OnliDealz.Black;

public class PayMod {

    //product,quantity,price,supply,all_pay,image,myPay
    String product=null;
    String quantity=null;
    String price=null;
    String supply=null;
    String all_pay=null;

    public PayMod(String product, String quantity, String price, String supply, String all_pay) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.supply = supply;
        this.all_pay = all_pay;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getAll_pay() {
        return all_pay;
    }

    public void setAll_pay(String all_pay) {
        this.all_pay = all_pay;
    }

}
