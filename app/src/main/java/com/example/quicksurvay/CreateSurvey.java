package com.example.quicksurvay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreateSurvey extends AppCompatActivity {

    private LinearLayout questionsLayout;
    private Button addQuestionButton, submitSurveyButton, shareSurveyButton;
    private int questionCount = 1;
    private ArrayList<String> surveyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        // Find views
        questionsLayout = findViewById(R.id.questionsLayout);
        addQuestionButton = findViewById(R.id.addQuestionButton);
        submitSurveyButton = findViewById(R.id.submitSurvey);
        shareSurveyButton = findViewById(R.id.shareSurveyButton);

        // Set up Add Question Button
        addQuestionButton.setOnClickListener(v -> addNewQuestion());

        // Set up Submit Survey Button
        submitSurveyButton.setOnClickListener(v -> submitSurvey());

        // Set up Share Survey Button
        shareSurveyButton.setOnClickListener(v -> shareSurvey());
    }

    // Method to dynamically add a new question to the layout
    private void addNewQuestion() {
        questionCount++;

        // Create new EditText for the survey question input
        EditText newSurveyQuestion = new EditText(this);
        newSurveyQuestion.setHint("Enter Survey Question");
        newSurveyQuestion.setTag("question_" + questionCount);

        // Create new LinearLayout for options (add as many as needed)
        LinearLayout optionsLayout = new LinearLayout(this);
        optionsLayout.setOrientation(LinearLayout.VERTICAL);

        // Create add option button
        Button addOptionButton = new Button(this);
        addOptionButton.setText("Add Option");

        // Set up add option functionality
        addOptionButton.setOnClickListener(v -> {
            // Dynamically create a new EditText for each option
            EditText option = new EditText(this);
            option.setHint("Option");
            optionsLayout.addView(option);
        });

        // Add everything to the questions layout
        questionsLayout.addView(newSurveyQuestion);
        questionsLayout.addView(addOptionButton);
        questionsLayout.addView(optionsLayout);

        // Scroll to the new question
        ScrollView surveyScrollView = findViewById(R.id.surveyScrollView);
        surveyScrollView.post(() -> surveyScrollView.fullScroll(View.FOCUS_DOWN));
    }

    // Method to handle survey submission
    private void submitSurvey() {
        // Clear existing data
        surveyData.clear();

        // Iterate over questionsLayout children to gather data
        for (int i = 0; i < questionsLayout.getChildCount(); i++) {
            View view = questionsLayout.getChildAt(i);

            if (view instanceof EditText) {
                // Get the question
                EditText questionView = (EditText) view;
                String question = questionView.getText().toString();

                if (!TextUtils.isEmpty(question)) {
                    surveyData.add("Question: " + question);
                }
            } else if (view instanceof LinearLayout) {
                // Get options from the inner LinearLayout
                LinearLayout optionsLayout = (LinearLayout) view;
                for (int j = 0; j < optionsLayout.getChildCount(); j++) {
                    View optionView = optionsLayout.getChildAt(j);
                    if (optionView instanceof EditText) {
                        String option = ((EditText) optionView).getText().toString();
                        if (!TextUtils.isEmpty(option)) {
                            surveyData.add("Option: " + option);
                        }
                    }
                }
            }
        }

        // Display the survey data (for testing purposes)
        for (String data : surveyData) {
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        }

        // You can now store this data in a database or process it further
    }

    // Method to share the survey
    private void shareSurvey() {
        // Build survey content as a text format to share
        StringBuilder surveyContent = new StringBuilder();
        for (String data : surveyData) {
            surveyContent.append(data).append("\n");
        }

        // Create an intent to share the survey
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, surveyContent.toString());

        startActivity(Intent.createChooser(shareIntent, "Share Survey via"));
    }
}
