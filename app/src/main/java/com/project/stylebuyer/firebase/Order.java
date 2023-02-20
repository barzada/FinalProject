package com.project.stylebuyer.firebase;

public class Order extends Uid {
    private Product product;
    private String size;
    private long orderTime;
    private String clientUid;

    public Order(String uid, Product product, String size, long orderTime, String clientUid) {
        super(uid);
        this.product = product;
        this.size = size;
        this.orderTime = orderTime;
        this.clientUid = clientUid;
    }

    public Order(){}

    public Product getProduct() {
        return product;
    }

    public Order setProduct(Product product) {
        this.product = product;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Order setSize(String size) {
        this.size = size;
        return this;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public Order setOrderTime(long orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public String getClientUid() {
        return clientUid;
    }

    public Order setClientUid(String clientUid) {
        this.clientUid = clientUid;
        return this;
    }
}
