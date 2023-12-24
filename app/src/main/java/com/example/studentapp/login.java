package com.example.studentapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
        Button login;

        EditText email,pass;
        ProgressDialog progressDialog;
        FirebaseAuth auth;
        FirebaseUser user;
        ProgressBar progressBar;
        static  final String TAG="Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        email=findViewById(R.id.editText_login_email);
        pass=findViewById(R.id.editText_login_pwd);
        login=findViewById(R.id.button_login);
        progressBar=findViewById(R.id.progressBar);


        progressDialog=new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        //show hide pass

        ImageView showhide=findViewById(R.id.imageView_show_hide_pwd);
        showhide.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);


        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                        //if pass is visible then hide it
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            //change icon
                            showhide.setImageResource(R.drawable.ic_baseline_hide_source_24);
                        }else {
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            showhide.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                        }
            }
        });

        //Login user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textemail=email.getText().toString();
                String textpswd=pass.getText().toString();

                if (TextUtils.isEmpty(textemail)){
                    Toast.makeText(login.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                    email.setError("Email is required");
                    email.requestFocus();

                }else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()){
                    Toast.makeText(login.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                    email.setError("Please enter valid email");
                    email.requestFocus();
                }else if (TextUtils.isEmpty(textpswd)){
                    Toast.makeText(login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    pass.setError("Password is required");
                    pass.requestFocus();
                }else{

                    progressBar.setVisibility(View.VISIBLE);
                    loginuser(textemail, textpswd);
                }

            }
        });
    }


    private void loginuser(String Email, String Pass) {
        auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(login.this, "You're Logged In",Toast.LENGTH_SHORT).show();

                        //start submain activity
                        startActivity(new Intent(login.this, submain.class));
                        finish();
                    }
                    else {
                        firebaseUser.sendEmailVerification();
                        auth.signOut();
                        showalert();
                    }
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                      email.setError("User does not exists");
                      email.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                      pass.setError("Invalid credentials please re-enter");
                      pass.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void showalert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(login.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email now. You cannot login without verifying your email.");

        //opens email app if user clicks continue
        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL); //to open email app list
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //open email in new window
                startActivity(intent);
            }
        });

        //create alert dialogbox
        AlertDialog alertDialog=builder.create();

        //show the alert dialog box
        alertDialog.show();;
    }

    //check if user is already logged in or not
    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!=null){
            Toast.makeText(login.this, "Already logged in", Toast.LENGTH_SHORT).show();

            //start submain activity
            startActivity(new Intent(login.this, submain.class));
            finish();
        }else {
            Toast.makeText(this, "You can login now", Toast.LENGTH_SHORT).show();

        }
    }
}