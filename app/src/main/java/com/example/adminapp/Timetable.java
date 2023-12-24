package com.example.adminapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Timetable extends AppCompatActivity {

    private CardView addimage;
    private final int REQ=1;
    private Bitmap bitmap;
    private ImageView noticeimageview;
    private Button upload,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        getSupportActionBar().setTitle("Time Table");

      /*  addimage=findViewById(R.id.addtt);
        noticeimageview=findViewById(R.id.ttimageview);
        upload=findViewById(R.id.uploadttbtn);
        back=findViewById(R.id.backbtn3);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Timetable.this, "Notice uploaded successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Timetable.this,Timetable.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Timetable.this,MainActivity.class);
                startActivity(intent);
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
            noticeimageview.setImageBitmap(bitmap);
        }*/
    }
}