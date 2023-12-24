package com.example.adminapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.adminapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class updatefaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView comp,mech,civil,elec;
    private LinearLayout csnodata,mechnodata,civilnodata,elecnodata;
    private List<TeacherData> list1,list2,list3,list4;

    private DatabaseReference reference, dbref;

    private teacheradapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefaculty);

        getSupportActionBar().setTitle("Faculty");

        comp=findViewById(R.id.csdept);
        mech=findViewById(R.id.mechdept);
        civil=findViewById(R.id.civildept);
        elec=findViewById(R.id.elecdept);


        csnodata=findViewById(R.id.csnodata);
        mechnodata=findViewById(R.id.mechnodata);
        civilnodata=findViewById(R.id.civilnodata);
        elecnodata=findViewById(R.id.elecnodata);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        mechDepartment();
        civilDepartment();
        elecDepartment();

        fab=findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updatefaculty.this,AddFaculty.class);
                startActivity(intent);
            }
        });
    }

    private void csDepartment() {
        dbref=reference.child("Computer Engineering");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1=new ArrayList<>();
                if (!datasnapshot.exists()){
                    csnodata.setVisibility(View.VISIBLE);
                    comp.setVisibility(View.GONE);
                }else {
                    csnodata.setVisibility(View.GONE);
                    comp.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    comp.setHasFixedSize(true);
                    comp.setLayoutManager(new LinearLayoutManager(updatefaculty.this));
                    adapter=new teacheradapter(list1,updatefaculty.this,"Computer Engineering");
                    comp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updatefaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mechDepartment() {
        dbref=reference.child("Mechanical Engineering");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2=new ArrayList<>();
                if (!datasnapshot.exists()){
                    mechnodata.setVisibility(View.VISIBLE);
                    mech.setVisibility(View.GONE);
                }else {
                    mechnodata.setVisibility(View.GONE);
                    mech.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    mech.setHasFixedSize(true);
                    mech.setLayoutManager(new LinearLayoutManager(updatefaculty.this));
                    adapter=new teacheradapter(list2,updatefaculty.this, "Mechanical Engineering");
                    mech.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updatefaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void civilDepartment() {
        dbref=reference.child("Civil Engineering");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list3=new ArrayList<>();
                if (!datasnapshot.exists()){
                    civilnodata.setVisibility(View.VISIBLE);
                    civil.setVisibility(View.GONE);
                }else {
                    civilnodata.setVisibility(View.GONE);
                    civil.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    civil.setHasFixedSize(true);
                    civil.setLayoutManager(new LinearLayoutManager(updatefaculty.this));
                    adapter=new teacheradapter(list3,updatefaculty.this,"Civil Engineering");
                    civil.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updatefaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void elecDepartment() {
        dbref=reference.child("Electrical Engineering");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list4=new ArrayList<>();
                if (!datasnapshot.exists()){
                    elecnodata.setVisibility(View.VISIBLE);
                    elec.setVisibility(View.GONE);
                }else {
                    elecnodata.setVisibility(View.GONE);
                    elec.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    elec.setHasFixedSize(true);
                    elec.setLayoutManager(new LinearLayoutManager(updatefaculty.this));
                    adapter=new teacheradapter(list4,updatefaculty.this,"Electrical Engineering");
                    elec.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updatefaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}