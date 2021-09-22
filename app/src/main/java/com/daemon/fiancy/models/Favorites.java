package com.daemon.fiancy.models;

public class Favorites {

    private String favouredUser;
    private String []advertisementKeys;

    public Favorites() {
    }

    public String getFavouredUser() {
        return favouredUser;
    }

    public void setFavouredUser(String favouredUser) {
        this.favouredUser = favouredUser;
    }

    public String[] getAdvertisementKeys() {
        return advertisementKeys;
    }

    public void setAdvertisementKeys(String[] advertisementKeys) {
        this.advertisementKeys = advertisementKeys;
    }
}
