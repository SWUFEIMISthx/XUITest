package com.thx.xuitest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.progress.ratingbar.RatingBar;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.phoneNumber.setText(maskPhoneNumber(feedback.getPhoneNumber()));
        holder.feedbackText.setText(feedback.getFeedbackText());
        holder.ratingBar.setRating(feedback.getRating());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        TextView feedbackText;
        RatingBar ratingBar;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.tv_phone_number);
            feedbackText = itemView.findViewById(R.id.tv_feedback_text);
            ratingBar = itemView.findViewById(R.id.ratingBar_feedback);
        }
    }

    private String maskPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }
}


