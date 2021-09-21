package com.daemon.fiancy.models;

import java.util.ArrayList;

public class Advertisements {
    private String fullname;
    private String age;
    private String gender;
    private String status;
    private String description;
    private String profession;
    private String address;
    private String phone;
    private String email;
    private String religion;
    private String minEducatuinLevel;
    private ArrayList<String> hobbiesList;
    private String image1;
    private String image2;
    private String image3;
    private String fee;
    private String discount;
    private String totatlFee;
    private Boolean liveAdvertisement = false;

    public Advertisements() {
    }

    public Advertisements(String fullname, String age, String gender, String status, String description, String profession,
                          String address, String phone, String email, String religion, String minEducatuinLevel, ArrayList<String> hobbiesList,
                          String image1, String image2, String image3, Boolean liveAdvertisement) {
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.description = description;
        this.profession = profession;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.religion = religion;
        this.minEducatuinLevel = minEducatuinLevel;
        this.hobbiesList = hobbiesList;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.liveAdvertisement = liveAdvertisement;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMinEducatuinLevel() {
        return minEducatuinLevel;
    }

    public void setMinEducatuinLevel(String minEducatuinLevel) {
        this.minEducatuinLevel = minEducatuinLevel;
    }

    public ArrayList<String> getHobbiesList() {
        return hobbiesList;
    }

    public void setHobbiesList(ArrayList<String> hobbiesList) {
        this.hobbiesList = hobbiesList;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Boolean getLiveAdvertisement() {
        return liveAdvertisement;
    }

    public void setLiveAdvertisement(Boolean liveAdvertisement) {
        this.liveAdvertisement = liveAdvertisement;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotatlFee() {
        return totatlFee;
    }

    public void setTotatlFee(String totatlFee) {
        this.totatlFee = totatlFee;
    }
}
