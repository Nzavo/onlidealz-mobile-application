package com.example.OnliDealz.Black;

public class ProductMod {
    //counter_id,supplier_id,business_name,category,type,system,qnty,price,quantity,charge,description,image,status,comment,reg_date
    String purchase_id = null;
    String counter_id = null;
    String supplier_id = null;
    String business_name = null;
    String category = null;
    String type = null;
    String price = null;
    String quantity = null;
    String charge = null;
    String description = null;
    String image = null;
    String status = null;
    String comment = null;
    String reg_date = null;
    String system = null;

    public ProductMod(String purchase_id, String counter_id, String supplier_id, String business_name, String category, String type, String price, String quantity, String charge, String description, String image, String status, String comment, String reg_date, String system) {
        this.purchase_id = purchase_id;
        this.counter_id = counter_id;
        this.supplier_id = supplier_id;
        this.business_name = business_name;
        this.category = category;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.charge = charge;
        this.description = description;
        this.image = image;
        this.status = status;
        this.comment = comment;
        this.reg_date = reg_date;
        this.system = system;
    }

    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
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

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {

        return price + " " + category + " " + description + " " + type + " " + supplier_id + " " + quantity;

    }
}
