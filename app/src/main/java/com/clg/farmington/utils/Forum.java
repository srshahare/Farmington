package com.clg.farmington.utils;

public class Forum {
    String description, time, title, uid, image;

    public Forum(String description, String time, String title, String uid, String image) {
        this.description = description;
        this.time = time;
        this.title = title;
        this.uid = uid;
        this.image = image;
    }

    public Forum() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
