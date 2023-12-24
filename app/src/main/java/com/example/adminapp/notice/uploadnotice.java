package com.example.adminapp.notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminapp.MainActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class uploadnotice extends AppCompatActivity {

    private TextView addimage;
    private final int REQ=1;
    private Bitmap bitmap;
    private Button upload,back;
    private EditText noticetitle, noticedescription;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnotice);

        getSupportActionBar().setTitle("Upload Notice");

        addimage=findViewById(R.id.addimage);
        upload=findViewById(R.id.uploadnoticebtn);
        back=findViewById(R.id.backbtn);
        noticetitle=findViewById(R.id.noticetitle);
        noticedescription = findViewById(R.id.noticedescription);
        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticetitle.getText().toString().isEmpty()) {
                    noticetitle.setError("Empty");
                    noticetitle.requestFocus();
                }else if (bitmap==null)
                {
                    uploaddata();
                }else {
                    uploadimage();
                }
                Intent intent=new Intent(uploadnotice.this,uploadnotice.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(uploadnotice.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void uploaddata() {
        reference =reference.child("Notice");
        final String uniquekey=reference.push().getKey();

        Calendar Date = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        String date = currentdate.format(Date.getTime());

        Calendar Time = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("hh-mm");
        String time = currenttime.format(Time.getTime());

        String title=noticetitle.getText().toString();
        String description = noticedescription.getText().toString();

        noticedata noticedata=new noticedata(title,downloadurl,uniquekey,date,time, description);
        reference.child(uniquekey).setValue(noticedata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(uploadnotice.this, "Notice uploaded succefully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(uploadnotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadimage() {
        pd.setMessage("Uploading....");
        pd.show();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filepath;
        filepath=storageReference.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(uploadnotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploaddata();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(uploadnotice.this, "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void opengallery() {
        Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK)
        {
            Uri uri= data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}