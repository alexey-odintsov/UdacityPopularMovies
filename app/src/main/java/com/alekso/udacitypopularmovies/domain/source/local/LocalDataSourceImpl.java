package com.alekso.udacitypopularmovies.domain.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.LocalDataSource;

/**
 * Created by alekso on 17/04/2017.
 */

public class LocalDataSourceImpl implements LocalDataSource {
    private static final String TAG = LocalDataSourceImpl.class.getSimpleName();

    private static LocalDataSourceImpl sInstance;
    private ContentResolver mContentResolver;

    /**
     * @param сontentResolver
     */
    private LocalDataSourceImpl(ContentResolver сontentResolver) {
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
    public void getFavoriteMovies(DataSource.LoadItemsListCallback<Movie> listener) {
        mContentResolver.query(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void addFavoriteMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.FavoriteMovieEntry.C_MOVIE_ID, movie.getId());
        cv.put(MovieContract.FavoriteMovieEntry.C_TITLE, movie.getTitle());
        mContentResolver.insert(MovieContract.FavoriteMovieEntry.CONTENT_URI, cv);
    }

    @Override
    public void removeFavoriteMovie(long movieId) {
        mContentResolver.delete(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                MovieContract.FavoriteMovieEntry.C_MOVIE_ID + " = ? ",
                new String[]{String.valueOf(movieId)});
    }

    @Override
    public void deleteAllFavoriteMovies() {
        mContentResolver.delete(MovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null);
    }
}
