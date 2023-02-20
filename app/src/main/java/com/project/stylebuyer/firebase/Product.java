package com.project.stylebuyer.firebase;

import java.io.Serializable;

public class Product extends Uid implements Serializable{

    private String name;
    private double price;
    private String categoryKey;
    private String imageUrl;

    public Product(){}

    public Product(String uid, String name, double price, String categoryKey, String imageUrl){
        super(uid);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryKey = categoryKey;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public Product setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
