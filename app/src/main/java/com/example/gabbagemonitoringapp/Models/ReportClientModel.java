package com.example.gabbagemonitoringapp.Models;

public class ReportClientModel {
    String id, binNumber, location, date,inspectionDate,inspectionCondition,status;

    public ReportClientModel(String id, String binNumber, String location,
                             String date, String inspectionDate, String inspectionCondition, String status) {
        this.id = id;
        this.binNumber = binNumber;
        this.location = location;
        this.date = date;
        this.inspectionDate = inspectionDate;
        this.inspectionCondition = inspectionCondition;
        this.status = status;
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
}
