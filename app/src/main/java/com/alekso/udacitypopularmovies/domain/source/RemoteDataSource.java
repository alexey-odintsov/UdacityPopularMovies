package com.alekso.udacitypopularmovies.domain.source;

import com.alekso.udacitypopularmovies.domain.model.Movie;

/**
 * Created by alekso on 25/04/2017.
 */

public interface RemoteDataSource {
    /**
     *
     * @param movieId
     * @param listener
     */
    void getMovieDetails(long movieId, DataSource.LoadItemCallback<Movie> listener);

    /**
     *
     * @param sort
     * @param callback
     */
    void getMovies(int sort, DataSource.LoadItemsListCallback<Movie> callback);
}
