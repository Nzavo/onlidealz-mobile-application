package com.example.OnliDealz.Black;

public class GetUpload {
    //counter_id,supplier_id,business_name,category,type,qnty,
    String counter_id = null;
    String supplier_id = null;
    String business_name = null;
    String category = null;
    String type = null;
    String qnty = null;

    public GetUpload(String counter_id, String supplier_id, String business_name, String category, String type, String qnty) {
        this.counter_id = counter_id;
        this.supplier_id = supplier_id;
        this.business_name = business_name;
        this.category = category;
        this.type = type;
        this.qnty = qnty;
    }

    public String getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(String counter_id) {
        this.counter_id = counter_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String qnty) {
        this.qnty = qnty;
    }

    @Override
    public String toString() {

        return business_name + " " + category + " " + supplier_id + " " + type;

    }
}
