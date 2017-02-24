package com.alekso.udacitypopularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Main app activity. Will list popular movies picture in a recyclerView.
 */
public class MainActivity extends AppCompatActivity implements MainFragment.OnMovieClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFragment = MainFragment.newInstance(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_content, mMainFragment).commit();

        requestMovies();
    }

    @Override
    public void onMovieClick(View view) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class));
    }

    /**
     * Requests list of movies through MoviesDB API
     */
    private void requestMovies() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                App.getTopRatedMoviesUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mMainFragment.setText("RESPONSE: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mMainFragment.setText("ERROR: " + error);
                    }
                });

        App.getInstance(this).addToRequestQueue(jsonRequest);
    }
}