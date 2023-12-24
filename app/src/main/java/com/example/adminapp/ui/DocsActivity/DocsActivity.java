package com.example.adminapp.ui.DocsActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.R;
import com.example.adminapp.ui.DocsActivity.DocumentApplication;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DocsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DocumentApplicationAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);

        getSupportActionBar().setTitle("Students Request");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DocumentApplicationAdapter();
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchDocumentApplications();
    }

    private void fetchDocumentApplications() {
        db.collection("document_applications")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentApplication> documentApplications = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        DocumentApplication application = document.toObject(DocumentApplication.class);
                        documentApplications.add(application);
                    }
                    adapter.setDocumentApplications(documentApplications);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to fetch document applications", Toast.LENGTH_SHORT).show();
                });
    }
}