package com.example.studentapp.ui.notice;

public class noticedata {
    String title,image,key,date,time, description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public noticedata(String title, String image, String key, String date, String time, String description) {
        this.title = title;
        this.image = image;
        this.key = key;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public noticedata() {
    }
}
