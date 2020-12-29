package com.clg.farmington.utils;

public class Product {
    String name, image, offer, price, uid;

    public Product() {}

    public Product(String name, String image, String offer, String price, String uid) {
        this.name = name;
        this.image = image;
        this.offer = offer;
        this.price = price;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
