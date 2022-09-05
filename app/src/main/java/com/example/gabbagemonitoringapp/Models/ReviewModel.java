package com.example.gabbagemonitoringapp.Models;

public class ReviewModel {
    String id, title, image, preview, author, status,suspend,reason;


    public ReviewModel(String id, String title, String image, String preview, String author, String reason
            , String status , String suspend) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.preview = preview;
        this.author = author;
        this.status = status;
        this.reason = reason;
        this.suspend = suspend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSuspend() {
        return suspend;
    }

    public void setSuspend(String suspend) {
        this.suspend = suspend;
    }
}
