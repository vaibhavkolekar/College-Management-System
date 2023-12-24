package com.example.studentapp.ui.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.studentapp.R;

import java.util.List;


public class teacheradapter extends RecyclerView.Adapter<teacheradapter.teacherviewadapter> {

    private List<TeacherData> list;
    private Context context;


    public teacheradapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public teacherviewadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facultyitemlayout, parent, false);
        return new teacherviewadapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherviewadapter holder, int position) {

        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Glide.with(context).load(item.getImage()).into(holder.imageview);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class teacherviewadapter extends RecyclerView.ViewHolder {

        private TextView name, email, post;

        private ImageView imageview;

        public teacherviewadapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teachername);
            email = itemView.findViewById(R.id.teachermail);
            post = itemView.findViewById(R.id.teacherpost);
            imageview = itemView.findViewById(R.id.teacherimg);

        }
    }
}
