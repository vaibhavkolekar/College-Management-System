package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentapp.ui.ApplyDocs.DocsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView textViewWelcome, textViewName, textViewMail, textViewDOB, textViewGender, textViewMobile, textViewPRN;
//    ProgressBar progressBar;
    String Name, Mail, DOB, Gender, Mobile;
    ImageView imageView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");

        textViewName = findViewById(R.id.textViewName);
        textViewPRN = findViewById(R.id.textViewPRN);
        textViewMail = findViewById(R.id.textViewMail);
        textViewDOB = findViewById(R.id.textViewDOB);
        textViewGender = findViewById(R.id.textViewGender);
        textViewMobile = findViewById(R.id.textViewMobileNo);


        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(profile.this, "Details Not Available", Toast.LENGTH_LONG).show();
        } else {
//            progressBar.setVisibility(View.VISIBLE);
            showProfile(firebaseUser);
        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String UserID = firebaseUser.getUid();

//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Registered User");
//        DatabaseReference userReference = FirebaseDatabase.child("users").child(UserID);
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Registered Users").child(UserID);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String mail = dataSnapshot.child("email").getValue(String.class);
                    String dob = dataSnapshot.child("dob").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String mobile = dataSnapshot.child("mobile").getValue(String.class);
                    String PRN = dataSnapshot.child("prn").getValue(String.class);

                    // Display user details
                    textViewName.setText(name);
                    textViewMail.setText(mail);
                    textViewDOB.setText(dob);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                    textViewPRN.setText(PRN);

//                    progressBar.setVisibility(View.GONE); // Hide progress bar


                } else {
                    Toast.makeText(profile.this, "User details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this,"Failed to Retrieve User Details",Toast.LENGTH_SHORT).show();
            }
        });
    }
}