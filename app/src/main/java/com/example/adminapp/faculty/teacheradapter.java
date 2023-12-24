package com.example.adminapp.faculty;

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

import com.example.adminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class teacheradapter extends RecyclerView.Adapter<teacheradapter.teacherviewadapter> {

    private List<TeacherData> list;
    private Context context;
    private String category;

    public teacheradapter(List<TeacherData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public teacherviewadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.facultyitemlayout,parent,false);
        return new teacherviewadapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherviewadapter holder, int position) {

        TeacherData item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try{
            Picasso.get().load(item.getImage()).into(holder.imageview);
        } catch (Exception e){
            e.printStackTrace();
        }


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,updateinfo.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class teacherviewadapter extends RecyclerView.ViewHolder {

        private TextView name,email,post;
        private Button update;
        private ImageView imageview;

        public teacherviewadapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teachername);
            email=itemView.findViewById(R.id.teachermail);
            post=itemView.findViewById(R.id.teacherpost);
            imageview=itemView.findViewById(R.id.teacherimg);
            update=itemView.findViewById(R.id.teacherupdate);
        }
    }
}
