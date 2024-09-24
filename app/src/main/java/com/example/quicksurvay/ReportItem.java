package com.example.quicksurvay;

public class ReportItem {
    private String surveyName;
    private String questionText;
    private String responseText;

    public ReportItem(String surveyName, String questionText, String responseText) {
        this.surveyName = surveyName;
        this.questionText = questionText;
        this.responseText = responseText;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getResponseText() {
        return responseText;
    }
}
