package com.example.quicksurvay;

public class OptionItem {
    private String optionText;
    private int responseCount;

    public OptionItem(String optionText, int responseCount) {
        this.optionText = optionText;
        this.responseCount = responseCount;
    }

    public String getOptionText() {
        return optionText;
    }

    public int getResponseCount() {
        return responseCount;
    }
}
