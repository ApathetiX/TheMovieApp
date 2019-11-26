package com.sameetahmed.themovieapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sameetahmed.themovieapp.Constants;
import com.sameetahmed.themovieapp.R;
import com.sameetahmed.themovieapp.adapter.MovieAdapter;
import com.sameetahmed.themovieapp.model.Movie;
import com.sameetahmed.themovieapp.model.moviejson.MovieResponse;
import com.sameetahmed.themovieapp.model.moviejson.MovieResult;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private MovieAdapter movieAdapter;
    private static int lastFirstVisiblePosition;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            lastFirstVisiblePosition = savedInstanceState.getInt(Constants.LAST_POSITION_KEY);
        }

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        // Construct new list of movies to store results
        movies = new ArrayList<>();

        movieAdapter = new MovieAdapter(movies, getApplicationContext());
        recyclerView.setAdapter(movieAdapter);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (isNetworkAvailable()) {
            if (mSharedPreferences.getBoolean(getString(R.string.show_popular_key), true)) {
                loadData(Constants.POPULAR_URL);
            } else if (mSharedPreferences.getBoolean(getString(R.string.saved_highest_rated), true)) {
                loadData(Constants.HIGHEST_RATED_URL);
            } else loadData(Constants.POPULAR_URL);
        }



    }

    private void loadData(String url) {
        StringRequest popularRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        StringRequest topRatedRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(popularRequest);
        requestQueue.add(topRatedRequest);
    }

    public void jsonParse(String json) throws JSONException {
        movies.clear(); // Clears the list of movies
        Gson gson = new Gson();
        MovieResponse movieResponse;
        movieResponse = gson.fromJson(json, MovieResponse.class);
        List<MovieResult> movieResults = movieResponse.getMovieResultList();
        for (MovieResult movieResult : movieResults) {
            movies.add(movieResult.makeMovie());
        }
        movieAdapter.replaceData(movies); // Reloads movie data into adapter
        // Restore scrolls position
        ((GridLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastFirstVisiblePosition, 0);
    }

    /**
     * This method checks for internet connectivty before requesting Movies from API
     **/
    private boolean isNetworkAvailable() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.LAST_POSITION_KEY, lastFirstVisiblePosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.favorites:
                Intent favoriteIntent = new Intent(this, FavoritesActivity.class);
                startActivity(favoriteIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastFirstVisiblePosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
}
