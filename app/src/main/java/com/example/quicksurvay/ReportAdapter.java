package com.example.quicksurvay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<ReportItem> reportItems;

    public ReportAdapter(List<ReportItem> reportItems) {
        this.reportItems = reportItems;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportItem reportItem = reportItems.get(position);
        holder.questionIdTextView.setText("Question ID: " + reportItem.getQuestionId());
        holder.responseCountTextView.setText("Responses: " + reportItem.getResponseCount());
    }

    @Override
    public int getItemCount() {
        return reportItems.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {

        public TextView questionIdTextView;
        public TextView responseCountTextView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            questionIdTextView = itemView.findViewById(R.id.questionIdTextView);
            responseCountTextView = itemView.findViewById(R.id.responseCountTextView);
        }
    }
}
