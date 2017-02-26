package com.alekso.udacitypopularmovies.domain.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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
    public static List<Movie> parseMoviesList(JSONObject response) {
        List<Movie> movies = new ArrayList<>();
        Log.d(TAG, "RESPONSE: " + response);

        Gson gson = new Gson();

        MoviesResponse moviesResponse = gson.fromJson(response.toString(), MoviesResponse.class);
        Log.d(TAG, "Movies: " + moviesResponse);

        if (moviesResponse != null) {
            for (MoviesResponse.MovieItem m : moviesResponse.results) {
                Movie.Builder builder = new Movie.Builder()
                        .setId(m.id)
                        .setTitle(m.title)
                        .setPoster(m.poster);
                Movie movie = new Movie(builder);
                movies.add(movie);
            }

            return movies;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Parses a movie details
     *
     * @param response
     * @return
     */
    public static Movie parseMovieDetails(JSONObject response) {
        Movie movie = null;
        Gson gson = new Gson();

        MovieDetailsResponse movieDetailsResponse = gson.fromJson(response.toString(), MovieDetailsResponse.class);
        movie = new Movie.Builder()
                .setId(movieDetailsResponse.id)
                .setTitle(movieDetailsResponse.title)
                .setOverview(movieDetailsResponse.overview)
                .setPoster(movieDetailsResponse.poster)
                .setDuration(movieDetailsResponse.duration)
                .setReleaseDate(movieDetailsResponse.releaseDate)
                .setRating(movieDetailsResponse.rating)
                .build();
        return movie;
    }


    /**
     * Response model for Movie details request
     */
    class MovieDetailsResponse {
        private long id;
        private String title;
        private String overview;
        private String poster;
        @SerializedName("runtime")
        private int duration;
        @SerializedName("release_date")
        private String releaseDate;
        @SerializedName("vote_average")
        private float rating;
    }

    /**
     * Response model form Movies list request
     */
    class MoviesResponse {
        List<MovieItem> results;

        class MovieItem {
            long id;
            String title;
            @SerializedName("poster_path")
            String poster;
        }
    }
}
