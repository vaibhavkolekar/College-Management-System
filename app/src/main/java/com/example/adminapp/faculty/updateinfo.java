package com.example.adminapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class updateinfo extends AppCompatActivity {
    private ImageView updateteacherimg;
    private EditText upadateteachernm,upadateteacheremail,upadateteacherpost;
    private Button updatebtn,deletebtn;

    private  String name,image,email,post,downloadurl,category,uniquekey;
    private  final int REQ=1;
    private Bitmap bitmap=null;

    private  StorageReference storageReference;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);

        getSupportActionBar().setTitle("Update Info");

        name=getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");
        email=getIntent().getStringExtra("email");
        post=getIntent().getStringExtra("post");

         uniquekey =getIntent().getStringExtra("key");
         category=getIntent().getStringExtra("category");

        updateteacherimg=findViewById(R.id.updateteacherimg);
        upadateteachernm=findViewById(R.id.upadateteachernm);
        upadateteacherpost=findViewById(R.id.upadateteacherpost);
        upadateteacheremail=findViewById(R.id.upadateteacheremail);
        updatebtn=findViewById(R.id.updatebtn);
        deletebtn=findViewById(R.id.deletebtn);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();

       try {
           Picasso.get().load(image).into(updateteacherimg);
       }catch (Exception e){
           e.printStackTrace();
       }

        upadateteacheremail.setText(email);
        upadateteachernm.setText(name);
        upadateteacherpost.setText(post);

        updateteacherimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=upadateteachernm.getText().toString();
                email=upadateteacheremail.getText().toString();
                post=upadateteacherpost.getText().toString();
                checkvalidation();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedata();
            }
        });
    }

    private void deletedata() {
        reference.child(category).child(uniquekey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(updateinfo.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(updateinfo.this,updatefaculty.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updateinfo.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkvalidation() {
       if(name.isEmpty()){
           upadateteachernm.setError("Empty");
           upadateteachernm.requestFocus();
       }else  if(post.isEmpty()){
           upadateteacherpost.setError("Empty");
           upadateteacherpost.requestFocus();
       }else  if(email.isEmpty()){
           upadateteacheremail.setError("Empty");
           upadateteacheremail.requestFocus();
       }else if(bitmap==null){
           updatedata(image);
       }else{
           uploadimage();
       }
    }

    private void uploadimage() {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filepath;
        filepath=storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(updateinfo.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl=String.valueOf(uri);
                                    updatedata(downloadurl);
                                }
                            });
                        }
                    });
                }else {
                    //pd.dismiss();
                    Toast.makeText(updateinfo.this, "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatedata(String s) {

        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        hp.put("image",s);


        reference.child(category).child(uniquekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(updateinfo.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(updateinfo.this,updatefaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(updateinfo.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery(){
        Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }

    protected  void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if (requestcode==REQ && resultcode==RESULT_OK){
            Uri uri= data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e){
                e.printStackTrace();
            }
            updateteacherimg.setImageBitmap(bitmap);
        }
    }
}