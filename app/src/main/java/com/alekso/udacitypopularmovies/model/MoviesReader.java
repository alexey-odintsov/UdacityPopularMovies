package com.alekso.udacitypopularmovies.model;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses data from JSON response
 * <p>
 * <p>
 * Created by alekso on 24/02/2017.
 */
public class MoviesReader {

    public static final String TAG = MoviesReader.class.getSimpleName();

    /**
     * Parses list of movies from JSON response
     *
     * @param response JSON response
     * @return List of movies
     */
    public static List<Movie> parse(JSONObject response) {
        Log.d(TAG, "RESPONSE: " + response);

        Gson gson = new Gson();

        MoviesResponse moviesResponse = gson.fromJson(response.toString(), MoviesResponse.class);
        Log.d(TAG, "Movies: " + moviesResponse);

        if (moviesResponse != null) {
            for (Movie m : moviesResponse.results) {
                Log.d(TAG, m.getTitle());
            }

            return moviesResponse.results;
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Class represents API response
     */
    class MoviesResponse {
        List<Movie> results;
    }
}
