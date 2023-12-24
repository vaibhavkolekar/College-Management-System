package com.example.studentapp.ui.home;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentapp.R;


public class homeFragment extends Fragment {
    Button locate;
    TextView dbatu, dte, ugc, aicte;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        locate = view.findViewById(R.id.locate);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://goo.gl/maps/wPqrh4aQ5QbeFxHz5");
            }
        });

        dbatu = view.findViewById(R.id.dbatu);
        dbatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://dbatu.ac.in");
            }
        });

        dte = view.findViewById(R.id.dte);
        dte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://dte.maharashtra.gov.in");
            }
        });

        aicte = view.findViewById(R.id.aicte);
        aicte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.aicte-india.org/");
            }
        });

        ugc = view.findViewById(R.id.ugc);
        ugc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("http://www.ugc.ac.in");
            }
        });
        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}