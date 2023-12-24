package com.example.studentapp.ui.ApplyDocs;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DocsActivity extends AppCompatActivity {

    private Spinner spnDocumentType;
    private EditText etReason;
    private Button btnApply;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);

        getSupportActionBar().setTitle("Apply");

        spnDocumentType = findViewById(R.id.document_type);
        etReason = findViewById(R.id.et_reason);
        btnApply = findViewById(R.id.btn_apply);

        db = FirebaseFirestore.getInstance();

        // Set up the document type options in the spinner
        List<String> documentTypes = DocumentApplication.getDocumentTypeOptions();
        ArrayAdapter<String> documentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentTypes);
        documentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDocumentType.setAdapter(documentTypeAdapter);

        TextView textViewPRN = findViewById(R.id.textViewPRN);
        String prn = getIntent().getStringExtra("PRN");
        if(prn != null){
            // Set PRN value in the corresponding TextView
            textViewPRN.setText(prn);
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDocument(prn);
            }
        });
    }

    private void applyDocument(String prn) {
        String documentType = spnDocumentType.getSelectedItem().toString();
        String reason = etReason.getText().toString();

        Calendar Date = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        String date = currentdate.format(Date.getTime());

        Calendar Time = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("hh-mm");
        String time = currenttime.format(Time.getTime());

        // Retrieve PRN from Firebase Realtime Database
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            DatabaseReference prnRef = FirebaseDatabase.getInstance().getReference().child("Registered Users").child(userID).child("prn");
            prnRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String prn = dataSnapshot.getValue(String.class);

                        // Create a new document application document
                        DocumentApplication application = new DocumentApplication(prn, documentType, reason, date, time);

                        db.collection("document_applications")
                                .add(application)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(DocsActivity.this, "Request For Documents submitted successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(DocsActivity.this, "Failed to submit document application", Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(DocsActivity.this, "PRN not found in database", Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DocsActivity.this, "Failed to retrieve PRN from database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}}