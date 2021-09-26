package com.daemon.fiancy.models;

public class RejectedAds {

    String reason;
    String rs;


    public RejectedAds() {

    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean ReasonNull(String rs){
        if(rs == null){
            return false;
        }else {
            return true;
        }
    }
}


