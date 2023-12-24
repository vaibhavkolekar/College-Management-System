package com.example.adminapp.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class deletenotice extends AppCompatActivity {

    private RecyclerView deletenoticerecycler;
    private ProgressBar progressbar;
    private ArrayList<noticedata> list;
    private noticeadapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletenotice);
        getSupportActionBar().setTitle("Notice");

        deletenoticerecycler = findViewById(R.id.deleteNoticeRecycler);
        progressbar = findViewById(R.id.progressbar);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        deletenoticerecycler.setLayoutManager(new LinearLayoutManager(this));
        deletenoticerecycler.setHasFixedSize(true);

        getNotice();
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    noticedata data = snapshot.getValue(noticedata.class);
                    list.add(0, data);
                }

                adapter = new noticeadapter(deletenotice.this, list);
                adapter.notifyDataSetChanged();
                progressbar.setVisibility(View.GONE);
                deletenoticerecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(deletenotice.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}