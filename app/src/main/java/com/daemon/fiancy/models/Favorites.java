package com.daemon.fiancy.models;

import java.util.ArrayList;

public class Favorites {

    private ArrayList<String> advertisementKeys;

    public Favorites() {
    }

    public ArrayList<String> getAdvertisementKeys() {
        return advertisementKeys;
    }

    public void setAdvertisementKeys(ArrayList<String> advertisementKeys) {
        this.advertisementKeys = advertisementKeys;
    }
}
