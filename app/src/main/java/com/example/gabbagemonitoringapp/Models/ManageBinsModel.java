package com.example.gabbagemonitoringapp.Models;

public class ManageBinsModel {
    String id, binNumber, location, date,inspectionDate,inspectionCondition,status,latitude,longitude;

    public ManageBinsModel(String id, String binNumber, String location,
                           String date, String inspectionDate, String inspectionCondition,
                           String status, String latitude, String longitude) {
        this.id = id;
        this.binNumber = binNumber;
        this.location = location;
        this.date = date;
        this.inspectionDate = inspectionDate;
        this.inspectionCondition = inspectionCondition;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionCondition() {
        return inspectionCondition;
    }

    public void setInspectionCondition(String inspectionCondition) {
        this.inspectionCondition = inspectionCondition;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
