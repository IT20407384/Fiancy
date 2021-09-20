package com.daemon.fiancy.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Advertisements implements Parcelable{
    private String fullname;
    private String age;
    private String gender;
    private String status;
    private String description;
    private String profession;
    private String address;
    private String phone;
    private String email;
    private String minEducatuinLevel;
    private ArrayList<String> hobbiesList;
    private String image1;
    private String image2;
    private String image3;

    public Advertisements() {}

    public Advertisements(String fullname, String age, String gender, String status, String description,
                          String profession, String address, String phone, String email, String minEducatuinLevel,
                          ArrayList<String> hobbiesList, String image1, String image2, String image3) {
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.description = description;
        this.profession = profession;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.minEducatuinLevel = minEducatuinLevel;
        this.hobbiesList = hobbiesList;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    protected Advertisements(Parcel in) {
        fullname = in.readString();
        age = in.readString();
        gender = in.readString();
        status = in.readString();
        description = in.readString();
        profession = in.readString();
        address = in.readString();
        phone = in.readString();
        email = in.readString();
        minEducatuinLevel = in.readString();
        hobbiesList = in.createStringArrayList();
        image1 = in.readString();
        image2 = in.readString();
        image3 = in.readString();
    }

    public static final Creator<Advertisements> CREATOR = new Creator<Advertisements>() {
        @Override
        public Advertisements createFromParcel(Parcel in) {
            return new Advertisements(in);
        }

        @Override
        public Advertisements[] newArray(int size) {
            return new Advertisements[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullname);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(status);
        dest.writeString(description);
        dest.writeString(profession);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(minEducatuinLevel);
        dest.writeStringList(hobbiesList);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(image3);
    }
}
