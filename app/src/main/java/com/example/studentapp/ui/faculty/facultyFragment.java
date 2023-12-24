package com.example.studentapp.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class facultyFragment extends Fragment {

    private RecyclerView comp,mech,civil,elec;
    private LinearLayout csnodata,mechnodata,civilnodata,elecnodata;
    private List<TeacherData> list1,list2,list3,list4;

    private DatabaseReference reference, dbref;

    private teacheradapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faculty, container, false);
        comp=view.findViewById(R.id.csdept);
        mech=view.findViewById(R.id.mechdept);
        civil=view.findViewById(R.id.civildept);
        elec=view.findViewById(R.id.elecdept);


        csnodata=view.findViewById(R.id.csnodata);
        mechnodata=view.findViewById(R.id.mechnodata);
        civilnodata=view.findViewById(R.id.civilnodata);
        elecnodata=view.findViewById(R.id.elecnodata);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        mechDepartment();
        civilDepartment();
        elecDepartment();



       return  view;
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
                    comp.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new teacheradapter(list1,getContext());
                    comp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mech.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new teacheradapter(list2,getContext());
                    mech.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    civil.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new teacheradapter(list3,getContext());
                    civil.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    elec.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new teacheradapter(list4,getContext());
                    elec.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}