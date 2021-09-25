package com.daemon.fiancy.models;

import java.util.ArrayList;

public class ReasonForDeleteAD {
    String documentKey;
    ArrayList<String> reasons;

    public ReasonForDeleteAD() {
    }

    public ReasonForDeleteAD(String documentKey, ArrayList<String> reasons) {
        this.documentKey = documentKey;
        this.reasons = reasons;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public ArrayList<String> getReasons() {
        return reasons;
    }

    public void setReasons(ArrayList<String> reasons) {
        this.reasons = reasons;
    }
}
