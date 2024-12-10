package com.batch.example.model;


public class Product {

    private Integer productId;
    private String title;
    private String description;
    private String price;
    private String discount;
    private String discountedPrice;

    public Product(){

    }

    public Product(Integer productId, String title, String description, String price, String discount, String discountedPrice) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
