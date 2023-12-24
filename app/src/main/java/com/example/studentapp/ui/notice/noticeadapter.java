package com.example.studentapp.ui.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentapp.R;

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
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        noticedata currentitem = list.get(position);

        holder.deletenoticetitle2.setText(currentitem.getTitle());

        try {
            if (currentitem.getImage() != null)
                Glide.with(context).load(currentitem.getImage()).into(holder.deletenoticeimage2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.noticedescription.setText(currentitem.description);
        holder.date.setText(currentitem.getDate());
        holder.time.setText(currentitem.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {


        private TextView deletenoticetitle2;
        private ImageView deletenoticeimage2;
        private TextView date;
        private TextView time;
        private TextView noticedescription;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            deletenoticetitle2 = itemView.findViewById(R.id.deleteNoticeTitle);
            deletenoticeimage2 = itemView.findViewById(R.id.deleteNoticeImage);
            noticedescription = itemView.findViewById(R.id.noticedescription);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }


}