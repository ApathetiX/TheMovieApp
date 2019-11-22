package com.sameetahmed.themovieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sameetahmed.themovieapp.adapter.MovieAdapter;
import com.sameetahmed.themovieapp.model.Movie;
import com.sameetahmed.themovieapp.model.json.Result;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private MovieAdapter movieAdapter;
    private static int lastFirstVisiblePosition;

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

        if (isNetworkAvailable()) {
            loadData(Constants.POPULAR_URL);
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
        com.sameetahmed.themovieapp.model.json.Response response;
        response = gson.fromJson(json, com.sameetahmed.themovieapp.model.json.Response.class);
        List<Result> results = response.getResultList();
        for (Result result : results) {
            movies.add(result.makeMovie());
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
    protected void onPause() {
        super.onPause();
        lastFirstVisiblePosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
}
