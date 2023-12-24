package com.example.adminapp;

import android.widget.RadioGroup;

public class readwriteUserDetails {
    public String dob,mobile, gender;
    String email, PRN, name;

    public readwriteUserDetails(String dob,String gender,String mobile,String email, String PRN, String name){

        this.dob=dob;
        this.gender=gender;
        this.mobile=mobile;
        this.email=email;
        this.PRN=PRN;
        this.name=name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrn() {
        return PRN;
    }

    public void setPrn(String PRN) {
        this.PRN = PRN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
