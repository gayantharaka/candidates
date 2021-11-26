package com.test.candidates.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Model implements Serializable {

    private boolean isSelected;
    private String image;
    private String title;
    private String firstName;
    private String lastName;
    private int age;
    private String dob;
    private String emailAddress;
    private String telephone;
    private String address;

    public Model(String image,String title, String firstName, String lastName, int age, String dob, String emailAddress, String telephone,String address,boolean isSelected) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dob = dob;
        this.emailAddress = emailAddress;
        this.telephone = telephone;
        this.title = title;
        this.address = address;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

