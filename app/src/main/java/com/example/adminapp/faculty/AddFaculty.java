package com.example.adminapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminapp.R;
import com.example.adminapp.notice.noticedata;
import com.example.adminapp.notice.uploadnotice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFaculty extends AppCompatActivity {

    private ImageView addteacherimage;
    private EditText addteachername,addteacheremail,addteacherpost;
    private Spinner addteacgercat;
    private Button addteacherbtn;
    private  final int REQ=1;
    private Bitmap bitmap=null;
    private String category,name,email,post,downloadurl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        addteachername=findViewById(R.id.addteachername);
        addteacheremail =findViewById(R.id.addteacheremail);
        addteacherimage=findViewById(R.id.addteacherimg);
        addteacherpost=findViewById(R.id.addteacherpost);
        addteacgercat=findViewById(R.id.addteachercat);
        addteacherbtn=findViewById(R.id.addteacherbutton);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();

        pd=new ProgressDialog(this);

        String[] items =new String[]{"Select Category","Computer Engineering","Mechanical Engineering","Civil Engineering","Electrical Engineering"};
        addteacgercat.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));

        addteacgercat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category=addteacgercat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addteacherimage.setOnClickListener( (view)-> {
            openGallery();
        });

        addteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvalidation();
            }
        });
    }

    private void checkvalidation() {
        name=addteachername.getText().toString();
        email=addteacheremail.getText().toString();
        post=addteacherpost.getText().toString();

        if (name.isEmpty()){
            addteachername.setError("Empty");
            addteachername.requestFocus();
        }else if (email.isEmpty()){
            addteacheremail.setError("Empty");
            addteacheremail.requestFocus();
        }else if (post.isEmpty()){
            addteacherpost.setError("Empty");
            addteacherpost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "Please provide teacher category", Toast.LENGTH_SHORT).show();
        }else if(bitmap==null){
            pd.setMessage("Uploading....");
            pd.show();
            insertdata();
        }else {
            pd.setMessage("Uploading....");
            pd.show();
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
        uploadTask.addOnCompleteListener(AddFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertdata();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddFaculty.this, "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertdata() {
        dbref = reference.child(category);

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Create the "Computer" node if it doesn't exist
                    dbref.setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                addTeacherToDatabase(dbref);
                            } else {
                                pd.dismiss();
                                Toast.makeText(AddFaculty.this, "Failed to create category node", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    addTeacherToDatabase(dbref);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTeacherToDatabase(DatabaseReference dbref) {
        final String uniquekey = dbref.push().getKey();
        TeacherData teacherdata = new TeacherData(name, email, post, downloadurl, uniquekey);
        dbref.child(uniquekey).setValue(teacherdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Teacher added successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
          addteacherimage.setImageBitmap(bitmap);
      }
    }
}