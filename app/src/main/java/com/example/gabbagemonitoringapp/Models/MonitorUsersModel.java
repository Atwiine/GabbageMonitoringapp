package com.example.gabbagemonitoringapp.Models;

public class MonitorUsersModel {
    String id, names,address,numberPickups,date;

    public MonitorUsersModel(String id, String names, String address, String numberPickups, String date) {
        this.id = id;
        this.names = names;
        this.address = address;
        this.numberPickups = numberPickups;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPickups() {
        return numberPickups;
    }

    public void setNumberPickups(String numberPickups) {
        this.numberPickups = numberPickups;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
