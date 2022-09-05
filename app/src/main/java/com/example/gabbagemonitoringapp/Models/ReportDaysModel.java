package com.example.gabbagemonitoringapp.Models;

public class ReportDaysModel {
    String id,  no_pickups, date;

    public ReportDaysModel(String id,  String no_pickups, String date) {
        this.id = id;
//        this.name = name;name,
        this.no_pickups = no_pickups;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

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
