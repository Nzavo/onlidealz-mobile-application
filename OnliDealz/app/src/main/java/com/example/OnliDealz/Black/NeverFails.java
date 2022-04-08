package com.example.OnliDealz.Black;

public class NeverFails {
    //product_id,category,type,description,image,quantity,price,stock
    String product_id = null;
    String category = null;
    String type = null;
    String description = null;
    String image = null;
    String quantity = null;
    String price = null;
    String stock = null;

    public NeverFails(String product_id, String category, String type, String description, String image, String quantity, String price, String stock) {
        this.product_id = product_id;
        this.category = category;
        this.type = type;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {

        return price + " " + category + " " + description + " " + type;

    }
}
