package com.example.studentapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class register extends AppCompatActivity {

    //define edittext variables defined in xml
    EditText name1,email1,dob1,mobile1,pass1,confirmpass1;
    ProgressBar progressBar;
    RadioGroup gender;
    RadioButton genderselected;

    DatePickerDialog picker;
    static final String TAG="register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        Toast.makeText(register.this, "You can register now", Toast.LENGTH_SHORT).show();

        progressBar=findViewById(R.id.progressBar);
        name1=findViewById(R.id.editText_register_full_name);
        email1=findViewById(R.id.editText_register_email);
        dob1=findViewById(R.id.editText_register_dob);
        mobile1=findViewById(R.id.editText_register_mobile);
        pass1=findViewById(R.id.editText_register_password);
        confirmpass1=findViewById(R.id.editText_register_password1);

        gender=findViewById(R.id.radio_group_register_gender);
        gender.clearCheck();

        //set datepicker for dob
        dob1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int day =calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                //date picker dialog
                picker=new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob1.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        Button register=findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedgenderId=gender.getCheckedRadioButtonId();
                genderselected=findViewById(selectedgenderId);

                //Obtain the entered data
                String name=name1.getText().toString();
                String email=email1.getText().toString();
                String dob=dob1.getText().toString();
                String mobile=mobile1.getText().toString();
                String pass=pass1.getText().toString();
                String conpass=confirmpass1.getText().toString();
                String textgender;

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(register.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    name1.setError("Full name is required");
                    name1.requestFocus();
                }else if (TextUtils.isEmpty(email)){
                    Toast.makeText(register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    email1.setError("Email is required");
                    email1.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){//to check valid email used patterd(regex) java provided inbuilt pattern for email,phone etc
                    Toast.makeText(register.this, "Please re-enter email", Toast.LENGTH_SHORT).show();
                    email1.setError("Valid email is required");
                    email1.requestFocus();
                }else if(TextUtils.isEmpty(dob)){
                    Toast.makeText(register.this,"Please enter your dob",Toast.LENGTH_SHORT).show();
                    dob1.setError(" Dob is required");
                    dob1.requestFocus();
                }else if(gender.getCheckedRadioButtonId()==-1){
                    Toast.makeText(register.this,"Please enter your gender",Toast.LENGTH_SHORT).show();
                    genderselected.setError(" Gender is required");
                    genderselected.requestFocus();
                }else if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(register.this,"Please enter your mobile no.",Toast.LENGTH_SHORT).show();
                    mobile1.setError(" Mobile no. is required");
                    mobile1.requestFocus();
                }else if (mobile.length() !=10){
                    Toast.makeText(register.this,"Please re-enter your mobile no.",Toast.LENGTH_SHORT).show();
                    mobile1.setError(" Mobile no. should be of 10 digits");
                    mobile1.requestFocus();
                }else if (TextUtils.isEmpty(pass)){
                    Toast.makeText(register.this,"Please enter Password",Toast.LENGTH_SHORT).show();
                    pass1.setError(" Password is required");
                    pass1.requestFocus();
                }else if(pass.length()<6){
                    Toast.makeText(register.this,"Password should be at least 6 digits",Toast.LENGTH_SHORT).show();
                    pass1.setError(" Password too weak");
                    pass1.requestFocus();
                }else if (TextUtils.isEmpty(conpass)){
                    Toast.makeText(register.this,"Please confirm your Password",Toast.LENGTH_SHORT).show();
                    confirmpass1.setError(" Password confirmation is required");
                    confirmpass1.requestFocus();
                }else if(!pass.equals(conpass)){
                    Toast.makeText(register.this,"Please enter same Password",Toast.LENGTH_SHORT).show();
                    confirmpass1.setError(" Password confirmation is required");
                    confirmpass1.requestFocus();

                    //clear the entereed password
                    pass1.clearComposingText();
                    confirmpass1.clearComposingText();
                }else{
                    textgender=genderselected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registeruser(name,email,dob,gender,mobile,pass);
                }

            }
        });
    }

    private void registeruser(String name, String email, String dob, RadioGroup gender, String mobile, String pass) {
        FirebaseAuth auth =FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(register.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user =auth.getCurrentUser();

                            //update display name of user
                            UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(profileChangeRequest);

                            //enter user data in firebase.
                            readwriteUserDetails writeUserDetails =new readwriteUserDetails(dob,gender,mobile,email);

                            //extracting user reference from database for "registered user";;to read or write data from database you need an instance ofdatabase reference
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Registered Users");
                            reference.child(user.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        //send verification mail
                                        user.sendEmailVerification();

                                        Toast.makeText(register.this, "User Registerd successfully. Please verify email", Toast.LENGTH_SHORT).show();

                                    //open home page after registration
                                    Intent intent =new Intent(register.this,login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                    finish();
                                    }else{
                                        Toast.makeText(register.this, "User Registeration unsuccessful.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }else{
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                pass1.setError("Your password is too weak. Kindly use mix of Alphabets, Numbers, Special characters ");
                                pass1.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                pass1.setError("Your email id is invalid or already in use ");
                                pass1.requestFocus();
                            }catch (FirebaseAuthUserCollisionException e){
                                pass1.setError("User is already registered with this email id. Use another email id.");
                                pass1.requestFocus();
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}