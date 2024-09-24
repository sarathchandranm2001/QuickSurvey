package com.example.quicksurvay;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final List<ReportItem> reportItems;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportItem reportItem = reportItems.get(position);
        holder.surveyNameTextView.setText("Survey: " + reportItem.getSurveyName());
        holder.questionTextView.setText("Question: " + reportItem.getQuestionText());
        holder.responseTextView.setText("Answer: " + reportItem.getResponseText());
    }

    @Override
    public int getItemCount() {
        return reportItems.size();
    }

    // Inner ViewHolder class
    public static class ReportViewHolder extends RecyclerView.ViewHolder {

        public TextView surveyNameTextView;
        public TextView questionTextView;
        public TextView responseTextView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            surveyNameTextView = itemView.findViewById(R.id.surveyNameTextView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            responseTextView = itemView.findViewById(R.id.responseTextView);
        }
    }
}
