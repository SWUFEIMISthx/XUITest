package com.thx.xuitest;

public class Feedback {
    private final String phoneNumber;
    private final String feedbackText;
    private final float rating;

    public Feedback(String phoneNumber, String feedbackText, float rating) {
        this.phoneNumber = phoneNumber;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public float getRating() {
        return rating;
    }
}
