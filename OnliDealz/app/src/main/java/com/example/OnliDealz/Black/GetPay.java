package com.example.OnliDealz.Black;

public class GetPay {//orders,shipping,discount,discounted,original
    //pay_id,mpesa,shipping,orders,total,quantity,customer_id,phone,delivery,location,status,disburse,shipper,shipment,customer,reg_date
    String pay_id = null;
    String mpesa = null;
    String orders = null;
    String shipping = null;
    String discount = null;
    String discounted = null;
    String original = null;
    String quantity = null;
    String customer_id = null;
    String phone = null;
    String delivery = null;
    String location = null;
    String address = null;
    String status = null;
    String disburse = null;
    String shipper = null;
    String shipment = null;
    String customer = null;
    String review = null;
    String reg_date = null;

    public GetPay(String pay_id, String mpesa, String orders, String shipping, String discount, String discounted, String original, String quantity, String customer_id, String phone, String delivery, String location, String address, String status, String disburse, String shipper, String shipment, String customer, String review, String reg_date) {
        this.pay_id = pay_id;
        this.mpesa = mpesa;
        this.orders = orders;
        this.shipping = shipping;
        this.discount = discount;
        this.discounted = discounted;
        this.original = original;
        this.quantity = quantity;
        this.customer_id = customer_id;
        this.phone = phone;
        this.delivery = delivery;
        this.location = location;
        this.address = address;
        this.status = status;
        this.disburse = disburse;
        this.shipper = shipper;
        this.shipment = shipment;
        this.customer = customer;
        this.review = review;
        this.reg_date = reg_date;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisburse() {
        return disburse;
    }

    public void setDisburse(String disburse) {
        this.disburse = disburse;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {

        return shipping + " " + status + " " + customer_id + " " + phone + "" + customer + "" + shipper + "" + discounted + "" + mpesa + "" + orders + "" + original;

    }
}