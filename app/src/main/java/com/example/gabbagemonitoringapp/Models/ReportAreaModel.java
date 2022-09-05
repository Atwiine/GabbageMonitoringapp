package com.example.gabbagemonitoringapp.Models;

public class ReportAreaModel {
    String id, name, no_pickups, date;

    public ReportAreaModel(String id, String name, String no_pickups, String date) {
        this.id = id;
        this.name = name;
        this.no_pickups = no_pickups;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo_pickups() {
        return no_pickups;
    }

    public void setNo_pickups(String no_pickups) {
        this.no_pickups = no_pickups;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
