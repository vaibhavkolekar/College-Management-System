package com.example.studentapp.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class noticeFragment extends Fragment {

    private RecyclerView deletenoticerecycler;
    private ProgressBar progressbar;
    private ArrayList<noticedata> list;
    private noticeadapter adapter;

    private DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notice, container, false);

        deletenoticerecycler=view.findViewById(R.id.deleteNoticeRecycler);
        progressbar=view.findViewById(R.id.progressbar);

        reference= FirebaseDatabase.getInstance().getReference().child("Notice");

        deletenoticerecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        deletenoticerecycler.setHasFixedSize(true);

        getnotice();

        return view;


    }

    private void getnotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    noticedata data=snapshot.getValue(noticedata.class);
                    list.add(0,data);
                }

                adapter=new noticeadapter(getContext(),list);
                adapter.notifyDataSetChanged();
                progressbar.setVisibility(View.GONE);
                deletenoticerecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);

                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}