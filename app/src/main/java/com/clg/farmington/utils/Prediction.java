package com.clg.farmington.utils;

public class Prediction {
    String uid, name, quantity;

    public Prediction() {}

    public Prediction(String uid, String name, String quantity) {
        this.uid = uid;
        this.name = name;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
