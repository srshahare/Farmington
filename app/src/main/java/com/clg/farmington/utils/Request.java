package com.clg.farmington.utils;

public class Request {
    String uid, product, quantity, name, waiting_time;

    public Request(String uid, String product, String quantity, String name, String waiting_time) {
        this.uid = uid;
        this.product = product;
        this.quantity = quantity;
        this.name = name;
        this.waiting_time = waiting_time;
    }

    public Request() {}

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(String waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
