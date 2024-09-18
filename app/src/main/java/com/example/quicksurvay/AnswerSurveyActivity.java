package com.example.quicksurvay;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerSurveyActivity extends AppCompatActivity {

    private SurveyDatabaseHelper dbHelper;
    private LinearLayout questionsLayout;
    private SQLiteDatabase db;
    private ArrayList<QuestionAnswerPair> questionAnswerPairs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_survey);

        dbHelper = new SurveyDatabaseHelper(this);
        questionsLayout = findViewById(R.id.questionsLayout); // A LinearLayout in ScrollView to hold the questions

        db = dbHelper.getReadableDatabase();

        // Fetch all questions from the database
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);

        if (cursor.moveToFirst()) {
            displayAllQuestions(cursor);
        } else {
            Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show();
        }

        // Submit the answers when button is clicked
        Button submitAnswerButton = findViewById(R.id.submitAnswerButton);
        submitAnswerButton.setOnClickListener(v -> submitAllAnswers());

        cursor.close();
    }

    // Function to dynamically display all questions
    @SuppressLint("Range")
    private void displayAllQuestions(Cursor cursor) {
        while (!cursor.isAfterLast()) {
            String questionText = cursor.getString(cursor.getColumnIndex("question_text"));
            int questionId = cursor.getInt(cursor.getColumnIndex("id"));

            // Dynamically create TextView for the question
            TextView questionTextView = new TextView(this);
            questionTextView.setText(questionText);
            questionsLayout.addView(questionTextView);

            // Dynamically create EditText for the answer
            EditText answerInput = new EditText(this);
            questionsLayout.addView(answerInput);

            // Store question ID and answer input field in the list
            questionAnswerPairs.add(new QuestionAnswerPair(questionId, answerInput));

            cursor.moveToNext();
        }
    }

    // Function to submit all answers at once
    private void submitAllAnswers() {
        boolean allAnswered = true;

        for (QuestionAnswerPair pair : questionAnswerPairs) {
            String answer = pair.answerInput.getText().toString();
            if (answer.isEmpty()) {
                allAnswered = false;
                break;
            } else {
                ContentValues values = new ContentValues();
                values.put("question_id", pair.questionId);
                values.put("response_text", answer);
                db.insert("responses", null, values);
            }
        }

        if (allAnswered) {
            Toast.makeText(this, "All answers submitted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
        }
    }

    // Class to hold question ID and its associated EditText for the answer
    private static class QuestionAnswerPair {
        int questionId;
        EditText answerInput;

        public QuestionAnswerPair(int questionId, EditText answerInput) {
            this.questionId = questionId;
            this.answerInput = answerInput;
        }
    }
}


