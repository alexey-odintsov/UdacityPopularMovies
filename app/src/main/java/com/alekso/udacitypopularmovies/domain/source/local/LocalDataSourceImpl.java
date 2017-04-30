package com.alekso.udacitypopularmovies.domain.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.LocalDataSource;

/**
 * Created by alekso on 17/04/2017.
 */

public class LocalDataSourceImpl implements LocalDataSource {
    private static final boolean debug = true;
    private static final String TAG = App.fullTag(LocalDataSourceImpl.class.getSimpleName());

    private static LocalDataSourceImpl sInstance;
    private ContentResolver mContentResolver;

    /**
     * @param сontentResolver
     */
    private LocalDataSourceImpl(ContentResolver сontentResolver) {
        if (debug) Log.d(TAG, "constructor(сontentResolver: " + сontentResolver + ")");
        mContentResolver = сontentResolver;
    }

    /**
     * @param сontentResolver
     * @return
     */
    public static LocalDataSourceImpl getInstance(ContentResolver сontentResolver) {
        if (sInstance == null) {
            sInstance = new LocalDataSourceImpl(сontentResolver);
        }

        return sInstance;
    }

    @Override
    public void getFavoriteMovies(DataSource.LoadItemsListCallback<Movie> callback) {
        if (debug) Log.d(TAG, "getFavoriteMovies(listener: " + callback + ")");

        mContentResolver.query(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void addFavoriteMovie(Movie movie) {
        if (debug) Log.d(TAG, "addFavoriteMovie(movie: " + movie + ")");

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.FavoriteMovieEntry.C_MOVIE_ID, movie.getId());
        cv.put(MovieContract.FavoriteMovieEntry.C_TITLE, movie.getTitle());
        mContentResolver.insert(MovieContract.FavoriteMovieEntry.CONTENT_URI, cv);
    }

    @Override
    public void removeFavoriteMovie(long movieId) {
        if (debug) Log.d(TAG, "removeFavoriteMovie(movieId: " + movieId + ")");

        mContentResolver.delete(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                MovieContract.FavoriteMovieEntry.C_MOVIE_ID + " = ? ",
                new String[]{String.valueOf(movieId)});
    }

    @Override
    public void deleteAllFavoriteMovies() {
        if (debug) Log.d(TAG, "deleteAllFavoriteMovies()");

        mContentResolver.delete(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null);
    }
}
