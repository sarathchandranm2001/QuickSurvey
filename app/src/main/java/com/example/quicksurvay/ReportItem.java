package com.example.quicksurvay;

public class ReportItem {
    private int questionId;
    private int responseCount;

    public ReportItem(int questionId, int responseCount) {
        this.questionId = questionId;
        this.responseCount = responseCount;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getResponseCount() {
        return responseCount;
    }
}
