package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adminapp.faculty.updatefaculty;
import com.example.adminapp.notice.deletenotice;
import com.example.adminapp.notice.uploadnotice;
import com.example.adminapp.ui.DocsActivity.DocsActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout unbutton=findViewById(R.id.un);
        LinearLayout requestbutton=findViewById(R.id.sr);
        LinearLayout managenotice=findViewById(R.id.mn);
        LinearLayout faculty=findViewById(R.id.uf);
        LinearLayout timetable=findViewById(R.id.tt);
        LinearLayout registration=findViewById(R.id.newreg);


        unbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, uploadnotice.class);
                startActivity(intent);
            }
        });

        managenotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, deletenotice.class);
                startActivity(intent);

            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, updatefaculty.class);
                startActivity(intent);
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Timetable.class);
                startActivity(intent);
            }
        });

        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DocsActivity.class);
                startActivity(intent);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });


    }
}