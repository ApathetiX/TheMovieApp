package com.sameetahmed.themovieapp.model.moviejson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<MovieResult> mMovieResultList;


    public List<MovieResult> getMovieResultList() {
        return mMovieResultList;
    }

    public void setMovieResultList(List<MovieResult> movieResultList) {
        this.mMovieResultList = movieResultList;
    }
}
