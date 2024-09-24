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
        String query = "SELECT s.name AS survey_name, q.question_text, r.response_text " +
                "FROM responses r " +
                "JOIN questions q ON r.question_id = q.id " +
                "JOIN surveys s ON q.survey_id = s.id";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String surveyName = cursor.getString(cursor.getColumnIndex("survey_name"));
            @SuppressLint("Range") String questionText = cursor.getString(cursor.getColumnIndex("question_text"));
            @SuppressLint("Range") String responseText = cursor.getString(cursor.getColumnIndex("response_text"));

            reportItems.add(new ReportItem(surveyName, questionText, responseText));
        }
        cursor.close();

        return reportItems;
    }
}

