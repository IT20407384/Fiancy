package com.daemon.fiancy.models;

public class ReportedADModel {

    String reportedAdKey;
    String email;
    String message;
    String reason;

    public ReportedADModel() { }

    public ReportedADModel(String reportedAdKey, String email, String message, String reason) {
        this.reportedAdKey = reportedAdKey;
        this.email = email;
        this.message = message;
        this.reason = reason;
    }

    public String getReportedAdKey() {
        return reportedAdKey;
    }

    public void setReportedAdKey(String reportedAdKey) {
        this.reportedAdKey = reportedAdKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
