package com.sameetahmed.themovieapp.model.reviewjson;

import com.google.gson.annotations.SerializedName;
import com.sameetahmed.themovieapp.model.Review;

public class ReviewResult {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public ReviewResult(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Review makeMovie() {
        Review review = new Review(getAuthor(), getContent());
        return review;
    }
}
