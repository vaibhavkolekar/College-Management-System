package com.example.studentapp;

import android.widget.RadioGroup;

public class readwriteUserDetails {
    public String dob,mobile;
    RadioGroup gender;
    String email;

    public readwriteUserDetails(String dob,RadioGroup gender,String mobile,String email){

        this.dob=dob;
        this.gender=gender;
        this.mobile=mobile;
        this.email=email;
    }
}
