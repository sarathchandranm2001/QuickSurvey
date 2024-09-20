package com.example.quicksurvay;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
        questionsLayout = findViewById(R.id.questionsLayout);

        db = dbHelper.getReadableDatabase();

        // Fetch all questions from the database
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);

        if (cursor.moveToFirst()) {
            displayAllQuestions(cursor);
        } else {
            Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show();
        }

        // Submit the answers when the button is clicked
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

            // Create TextView for the question
            TextView questionTextView = new TextView(this);
            questionTextView.setText(questionText);
            questionsLayout.addView(questionTextView);

            // Check if options are available for the question
            Cursor optionsCursor = db.rawQuery("SELECT * FROM options WHERE question_id = ?", new String[]{String.valueOf(questionId)});
            if (optionsCursor.moveToFirst()) {
                // Create a RadioGroup for the options
                RadioGroup radioGroup = new RadioGroup(this);
                do {
                    String optionText = optionsCursor.getString(optionsCursor.getColumnIndex("option_text"));
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(optionText);
                    radioGroup.addView(radioButton);
                } while (optionsCursor.moveToNext());

                questionsLayout.addView(radioGroup);
                questionAnswerPairs.add(new QuestionAnswerPair(questionId, radioGroup));

            } else {
                // Create EditText for the answer if no options are available
                EditText answerInput = new EditText(this);
                questionsLayout.addView(answerInput);
                questionAnswerPairs.add(new QuestionAnswerPair(questionId, answerInput));
            }
            optionsCursor.close();
            cursor.moveToNext();
        }
    }

    // Function to submit all answers at once
    private void submitAllAnswers() {
        boolean allAnswered = true;

        for (QuestionAnswerPair pair : questionAnswerPairs) {
            if (pair.answerInput instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) pair.answerInput;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    allAnswered = false;
                    break;
                } else {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String answer = selectedRadioButton.getText().toString();
                    saveAnswer(pair.questionId, answer);
                }
            } else if (pair.answerInput instanceof EditText) {
                EditText answerInput = (EditText) pair.answerInput;
                String answer = answerInput.getText().toString();
                if (answer.isEmpty()) {
                    allAnswered = false;
                    break;
                } else {
                    saveAnswer(pair.questionId, answer);
                }
            }
        }

        if (allAnswered) {
            Toast.makeText(this, "All answers submitted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to save the answer in the database
    private void saveAnswer(int questionId, String answer) {
        ContentValues values = new ContentValues();
        values.put("question_id", questionId);
        values.put("response_text", answer);
        db.insert("responses", null, values);
    }

    // Class to hold question ID and its associated input field (RadioGroup or EditText)
    private static class QuestionAnswerPair {
        int questionId;
        Object answerInput;  // Can be RadioGroup or EditText

        public QuestionAnswerPair(int questionId, Object answerInput) {
            this.questionId = questionId;
            this.answerInput = answerInput;
        }
    }
}
