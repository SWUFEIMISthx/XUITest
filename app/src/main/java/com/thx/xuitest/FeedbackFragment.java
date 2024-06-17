package com.thx.xuitest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.progress.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFragment extends Fragment {

    private MaterialEditText etFeedback;
    private RatingBar ratingBar;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> feedbackList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        etFeedback = view.findViewById(R.id.et_feedback);
        ratingBar = view.findViewById(R.id.ratingBar);
        recyclerViewFeedback = view.findViewById(R.id.recyclerView_feedback);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedbackList);
        recyclerViewFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFeedback.setAdapter(feedbackAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {
        String feedbackText = etFeedback.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (feedbackText.isEmpty()) {
            Toast.makeText(getContext(), "请输入反馈内容", Toast.LENGTH_SHORT).show();
            return;
        }


        String phoneNumber = "17725013139";

        Feedback feedback = new Feedback(phoneNumber, feedbackText, rating);
        feedbackList.add(feedback);
        feedbackAdapter.notifyDataSetChanged();

        etFeedback.setText("");
        ratingBar.setRating(0);

        Toast.makeText(getContext(), "反馈提交成功", Toast.LENGTH_SHORT).show();
    }
}

