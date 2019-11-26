package com.sameetahmed.themovieapp.model.moviejson;

import com.google.gson.annotations.SerializedName;
import com.sameetahmed.themovieapp.model.Movie;

public class MovieResult {
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieResult(double popularity, int voteCount, String posterPath, int id, String title, String overview, String releaseDate) {
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie makeMovie() {
        Movie movie = new Movie(getPopularity(), getVoteCount(),
                getPosterPath(), getId(), getTitle(), getOverview(), getReleaseDate());
        return movie;
    }
}
