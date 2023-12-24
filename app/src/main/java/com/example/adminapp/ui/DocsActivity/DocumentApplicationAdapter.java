package com.example.adminapp.ui.DocsActivity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.R;

import java.util.ArrayList;
import java.util.List;

public class DocumentApplicationAdapter extends RecyclerView.Adapter<DocumentApplicationAdapter.ViewHolder> {

    private List<DocumentApplication> documentApplications = new ArrayList<>();

    public void setDocumentApplications(List<DocumentApplication> documentApplications) {
        this.documentApplications = documentApplications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentApplication application = documentApplications.get(position);
        holder.bind(application);
    }

    @Override
    public int getItemCount() {
        return documentApplications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPRN;
        private TextView textViewDocumentType;
        private TextView textViewReason;
        private TextView textViewDate;
        private TextView textViewTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPRN = itemView.findViewById(R.id.textViewPRN);
            textViewDocumentType = itemView.findViewById(R.id.textViewDocumentType);
            textViewReason = itemView.findViewById(R.id.textViewReason);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }

        public void bind(DocumentApplication application) {
            textViewPRN.setText(application.getPrn());
            textViewDocumentType.setText(application.getDocumentType());
            textViewReason.setText(application.getReason());
            textViewDate.setText(application.getDate());
            textViewTime.setText(application.getTime());
        }
    }
}
