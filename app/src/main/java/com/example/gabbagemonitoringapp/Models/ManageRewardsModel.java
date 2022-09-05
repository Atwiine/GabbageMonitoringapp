package com.example.gabbagemonitoringapp.Models;

public class ManageRewardsModel {
    String id, names,address,numberPickups,status,requested_reward;

    public ManageRewardsModel(String id, String names, String address, String numberPickups, String status , String requested_reward) {
        this.id = id;
        this.names = names;
        this.address = address;
        this.numberPickups = numberPickups;
        this.status = status;
        this.requested_reward = requested_reward;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequested_reward() {
        return requested_reward;
    }

    public void setRequested_reward(String requested_reward) {
        this.requested_reward = requested_reward;
    }
}
