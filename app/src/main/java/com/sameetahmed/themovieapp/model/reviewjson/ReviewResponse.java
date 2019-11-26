package com.sameetahmed.themovieapp.model.reviewjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("results")
    private List<ReviewResult> mReviewResults;

    public List<ReviewResult> getReviewResults() {
        return mReviewResults;
    }

    public void setReviewResults(List<ReviewResult> reviewResults) {
        mReviewResults = reviewResults;
    }
}
