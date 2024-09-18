package com.example.quicksurvay;



import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private SurveyDatabaseHelper dbHelper;
    private TextView reportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbHelper = new SurveyDatabaseHelper(this);
        reportTextView = findViewById(R.id.reportTextView);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT question_id, COUNT(*) as response_count FROM responses GROUP BY question_id", null);

        StringBuilder report = new StringBuilder();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int questionId = cursor.getInt(cursor.getColumnIndex("question_id"));
            @SuppressLint("Range") int responseCount = cursor.getInt(cursor.getColumnIndex("response_count"));
            report.append("Question ID: ").append(questionId).append(" - Responses: ").append(responseCount).append("\n");
        }
        cursor.close();

        reportTextView.setText(report.toString());
    }
}
