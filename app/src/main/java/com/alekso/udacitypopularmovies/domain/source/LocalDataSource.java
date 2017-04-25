package com.alekso.udacitypopularmovies.domain.source;

import com.alekso.udacitypopularmovies.domain.model.Movie;

/**
 * Created by alekso on 25/04/2017.
 */

public interface LocalDataSource {
    /**
     * @param listener
     */
    void getFavoriteMovies(DataSource.LoadItemsListCallback<Movie> listener);

    /**
     * @param movie
     */
    void addFavoriteMovie(Movie movie);

    /**
     * @param movieId
     */
    void removeFavoriteMovie(long movieId);

    /**
     *
     */
    void deleteAllFavoriteMovies();
}
