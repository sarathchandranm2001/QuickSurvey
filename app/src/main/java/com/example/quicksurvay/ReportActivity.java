package com.example.quicksurvay;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private SurveyDatabaseHelper dbHelper;
    private RecyclerView reportRecyclerView;
    private ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbHelper = new SurveyDatabaseHelper(this);
        reportRecyclerView = findViewById(R.id.reportRecyclerView);
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch report data
        List<ReportItem> reportItems = fetchReportData();

        // Set adapter
        reportAdapter = new ReportAdapter(reportItems);
        reportRecyclerView.setAdapter(reportAdapter);
    }

    private List<ReportItem> fetchReportData() {
        List<ReportItem> reportItems = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT question_id, COUNT(*) as response_count FROM responses GROUP BY question_id", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int questionId = cursor.getInt(cursor.getColumnIndex("question_id"));
            @SuppressLint("Range") int responseCount = cursor.getInt(cursor.getColumnIndex("response_count"));

            reportItems.add(new ReportItem(questionId, responseCount));
        }
        cursor.close();

        return reportItems;
    }
}
