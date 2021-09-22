package com.daemon.fiancy.models;

import java.util.ArrayList;

public class Favorites {

    private String favouredUser;
    private ArrayList<String> advertisementKeys;

    public Favorites() {
    }

    public String getFavouredUser() {
        return favouredUser;
    }

    public void setFavouredUser(String favouredUser) {
        this.favouredUser = favouredUser;
    }

    public ArrayList<String> getAdvertisementKeys() {
        return advertisementKeys;
    }

    public void setAdvertisementKeys(ArrayList<String> advertisementKeys) {
        this.advertisementKeys = advertisementKeys;
    }
}
