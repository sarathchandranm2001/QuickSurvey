package com.example.quicksurvay;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateSurveyActivity extends AppCompatActivity {

    private SurveyDatabaseHelper dbHelper;
    private EditText surveyNameInput;
    private LinearLayout questionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        dbHelper = new SurveyDatabaseHelper(this);

        surveyNameInput = findViewById(R.id.surveyNameInput);
        questionsLayout = findViewById(R.id.questionsLayout);

        Button addQuestionButton = findViewById(R.id.addQuestionButton);
        Button submitSurveyButton = findViewById(R.id.submitSurveyButton);

        // Adding new question dynamically
        addQuestionButton.setOnClickListener(v -> addQuestionView());

        // Submitting the survey
        submitSurveyButton.setOnClickListener(v -> {
            String surveyName = surveyNameInput.getText().toString();
            if (!surveyName.isEmpty() && questionsLayout.getChildCount() > 0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Insert survey name
                ContentValues surveyValues = new ContentValues();
                surveyValues.put("name", surveyName);
                long surveyId = db.insert("surveys", null, surveyValues);

                // Insert each question and its options
                for (int i = 0; i < questionsLayout.getChildCount(); i++) {
                    View questionView = questionsLayout.getChildAt(i);

                    EditText questionInput = questionView.findViewById(R.id.questionInput);
                    EditText option1Input = questionView.findViewById(R.id.option1Input);
                    EditText option2Input = questionView.findViewById(R.id.option2Input);
                    EditText option3Input = questionView.findViewById(R.id.option3Input);

                    String questionText = questionInput.getText().toString();
                    String option1 = option1Input.getText().toString();
                    String option2 = option2Input.getText().toString();
                    String option3 = option3Input.getText().toString();

                    // Insert question
                    ContentValues questionValues = new ContentValues();
                    questionValues.put("survey_id", surveyId);
                    questionValues.put("question_text", questionText);
                    long questionId = db.insert("questions", null, questionValues);

                    // Insert options
                    ContentValues optionValues = new ContentValues();
                    optionValues.put("question_id", questionId);

                    if (!option1.isEmpty()) {
                        optionValues.put("option_text", option1);
                        db.insert("options", null, optionValues);
                    }

                    if (!option2.isEmpty()) {
                        optionValues.put("option_text", option2);
                        db.insert("options", null, optionValues);
                    }

                    if (!option3.isEmpty()) {
                        optionValues.put("option_text", option3);
                        db.insert("options", null, optionValues);
                    }
                }

                Toast.makeText(CreateSurveyActivity.this, "Survey Created", Toast.LENGTH_SHORT).show();
                // Go back to MainActivity
                Intent intent = new Intent(CreateSurveyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(CreateSurveyActivity.this, "Please fill survey name and questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to dynamically add a question view
    private void addQuestionView() {
        View questionView = getLayoutInflater().inflate(R.layout.question_item, null);
        questionsLayout.addView(questionView);
    }
    public void removeQuestion(View view) {
        // Get the parent view of the delete button, which is the question layout
        View questionView = (View) view.getParent();
        // Remove the question from the parent LinearLayout
        questionsLayout.removeView(questionView);
    }

}
