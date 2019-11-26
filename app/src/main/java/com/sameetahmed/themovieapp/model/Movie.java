package com.sameetahmed.themovieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private Movie movie;
    private double popularity;
    private int voteCount;
    private String posterPath;
    private int id;
    private String title;
    private String overview;
    private String releaseDate;

    public Movie(double popularity, int voteCount, String posterPath, int id, String title, String overview, String releaseDate) {
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie(int movieId) {
        this.id = movieId;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeString(posterPath);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    protected Movie(Parcel in) {
        popularity = in.readDouble();
        voteCount = in.readInt();
        posterPath = in.readString();
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
