package com.alekso.udacitypopularmovies.domain.source;

import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public interface DataSource {
    public static final int SORT_POPULARITY = 0;
    public static final int SORT_TOP_RATED = 1;


    /**
     * Loads list of movies
     */
    void getMovies(int sort, LoadMoviesListener listener);

    void getMovieDetails(long movieId, LoadMovieDetailsListener listener);

    interface LoadMoviesListener {
        void onSuccess(List<Movie> movies);

        void onError(String message);
    }

    interface LoadMovieDetailsListener {
        void onSuccess(Movie movie);

        void onError(String message);
    }
}
