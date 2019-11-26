package com.sameetahmed.themovieapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sameetahmed.themovieapp.Constants;
import com.sameetahmed.themovieapp.R;
import com.sameetahmed.themovieapp.adapter.ReviewAdapter;
import com.sameetahmed.themovieapp.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsActivity extends AppCompatActivity {
    private int mMovieId;
    private List<Review> mReviewList = new ArrayList<>();
    private Review mReview;
    private RecyclerView mRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private static Bundle mSaveReviewView;
    private static String RECYCLER_VIEW_KEY = "recyclerViewKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = findViewById(R.id.recycler_reviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter(mReviewList, getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mMovieId = bundle.getInt("movieId");
        loadReviews();
    }


    public void loadReviews() {
        StringRequest reviewRequest = new StringRequest(Request.Method.GET,
                buildReviewRequest(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    buildReviewList(response);
                    mRecyclerView.setAdapter(mReviewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(reviewRequest);
    }

    private String buildReviewRequest(int movieId) {
        String BASE_URL = "https://api.themoviedb.org/3/movie";
        Uri movieReviewUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(Integer.toString(movieId))
                .appendPath("reviews")
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build();

        String finalUrl = movieReviewUri.toString();
        return finalUrl;
    }

    private void buildReviewList(String response) throws JSONException {
        mReviewList.clear(); // Clears the list of reviews
        JSONObject jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject objects = results.getJSONObject(i);
            mReview = new Review(
                    objects.getString("author"),
                    objects.getString("content")
            );
            mReviewList.add(mReview);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSaveReviewView = new Bundle();
        Parcelable movieListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mSaveReviewView.putParcelable(RECYCLER_VIEW_KEY, movieListState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSaveReviewView != null) {
            Parcelable movieListState = mSaveReviewView.getParcelable(RECYCLER_VIEW_KEY);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(movieListState);
        }
    }


}
