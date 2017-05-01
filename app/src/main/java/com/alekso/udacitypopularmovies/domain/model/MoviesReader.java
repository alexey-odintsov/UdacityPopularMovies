package com.alekso.udacitypopularmovies.domain.model;

import android.database.Cursor;
import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.alekso.udacitypopularmovies.domain.source.local.MovieContract.FavoriteMovieEntry.C_MOVIE_ID;
import static com.alekso.udacitypopularmovies.domain.source.local.MovieContract.FavoriteMovieEntry.C_POSTER;
import static com.alekso.udacitypopularmovies.domain.source.local.MovieContract.FavoriteMovieEntry.C_TITLE;

/**
 * Parses data from JSON response
 * <p>
 * <p>
 * Created by alekso on 24/02/2017.
 */
public class MoviesReader {

    public static final String TAG = App.fullTag(MoviesReader.class.getSimpleName());
    public static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static List<Movie> fromCursor(Cursor c) {
        List<Movie> movies = new ArrayList<>();

        while (c.moveToNext()) {
            Movie movie = new Movie.Builder()
                    .setId(c.getLong(c.getColumnIndex(C_MOVIE_ID)))
                    .setTitle(c.getString(c.getColumnIndex(C_TITLE)))
                    .setPoster(c.getString(c.getColumnIndex(C_POSTER)))
                    .build();
            movies.add(movie);
            Log.d(TAG, "movie parsed: " + movie);
        }
        c.close();

        return movies;
    }

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
        Gson gson = new Gson();


        MovieDetailsResponse movieDetailsResponse = gson.fromJson(response.toString(), MovieDetailsResponse.class);


        Movie.Builder builder = new Movie.Builder()
                .setId(movieDetailsResponse.id)
                .setTitle(movieDetailsResponse.title)
                .setOriginalTitle(movieDetailsResponse.originalTitle)
                .setOverview(movieDetailsResponse.overview)
                .setPoster(movieDetailsResponse.poster)
                .setDuration(movieDetailsResponse.duration)
                .setRating(movieDetailsResponse.rating)
                .setBackdrop(movieDetailsResponse.backdrop);

        try {
            Date date = sDateFormat.parse(movieDetailsResponse.releaseDate);
            builder.setReleaseDate(DateFormat.getDateInstance().format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            builder.setReleaseDate(movieDetailsResponse.releaseDate);
        }

        return builder.build();
    }


    /**
     * Response model for Movie details request
     */
    class MovieDetailsResponse {
        private long id;
        @SerializedName("original_title")
        private String originalTitle;
        private String title;
        private String overview;
        @SerializedName("poster_path")
        private String poster;
        @SerializedName("backdrop_path")
        private String backdrop;
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
