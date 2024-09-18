package com.example.quicksurvay;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SurveyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SurveyDatabaseHelper(this);

        Button createSurveyButton = findViewById(R.id.createSurveyButton);
        Button answerSurveyButton = findViewById(R.id.answerSurveyButton);
        Button reportButton = findViewById(R.id.reportButton);
        Button deleteDataButton = findViewById(R.id.deleteDataButton);

        createSurveyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateSurveyActivity.class);
            startActivity(intent);
        });

        answerSurveyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AnswerSurveyActivity.class);
            startActivity(intent);
        });

        reportButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(intent);
        });

        // Delete all data from the database
        deleteDataButton.setOnClickListener(v -> {
            dbHelper.deleteAllData();
            Toast.makeText(MainActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
        });
    }
}
