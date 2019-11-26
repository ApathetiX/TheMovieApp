package com.sameetahmed.themovieapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sameetahmed.themovieapp.activities.DetailActivity;
import com.sameetahmed.themovieapp.R;
import com.sameetahmed.themovieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private List<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(List<Movie> movies, Context context) {
        mMovies = movies;
        mContext = context;
    }

    /**
     * This method reloads the adapter with new data
     **/
    public void replaceData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterPath;
        public View movieView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterPath = itemView.findViewById(R.id.movie_poster);
            movieView = itemView;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       final Movie movie = mMovies.get(position);

        /** Allows click handling of each Movie and passes data to DetailActivity **/
        holder.movieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movieParcel = movie;
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("PARCEL_KEY_MAIN", movieParcel);

                mContext.startActivity(intent);
            }
        });

        Picasso.get()
                .load(BASE_IMAGE_URL + movie.getPosterPath())
                .into(holder.posterPath);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.size();
    }
}
