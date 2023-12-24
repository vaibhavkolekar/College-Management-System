package com.example.adminapp.notice;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class noticeadapter extends RecyclerView.Adapter<noticeadapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<noticedata> list;

    public noticeadapter(Context context, ArrayList<noticedata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        noticedata currentitem=list.get(position);

        holder.deletenoticetitle.setText(currentitem.getTitle());

        try {
            if (currentitem.getImage()!=null)
            Picasso.get().load(currentitem.getImage()).into(holder.deletenoticeimage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deletenotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                reference.child(currentitem.getKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                notifyItemRemoved(position);
            }
        });
        holder.date.setText(currentitem.getDate());
        holder.time.setText(currentitem.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private Button deletenotice;
        private TextView deletenoticetitle;
        private ImageView deletenoticeimage;
        private TextView date;
        private TextView time;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deletenotice=itemView.findViewById(R.id.deletenotice);
            deletenoticetitle=itemView.findViewById(R.id.deleteNoticeTitle);
            deletenoticeimage=itemView.findViewById(R.id.deleteNoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }
    }


}