package com.alekso.udacitypopularmovies.domain.source;

import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alekso on 26/02/2017.
 */

public class Repository implements LocalDataSource, RemoteDataSource {
    private static final boolean debug = true;
    private static final String TAG = App.fullTag(Repository.class.getSimpleName());

    /**
     * Simple in-memory cache for movies list
     */
    public static Map<Integer, List<Movie>> sMoviesListCache = new HashMap<>(2);
    /**
     * Simple in-memory cache for movies details
     */
    public static Map<Long, Movie> sMoviesDetailsCache = new HashMap<>();
    /**
     * Repository instance field
     */
    private static Repository sInstance;
    /**
     * Remote data source - server
     */
    private final RemoteDataSource mRemoteDataSource;
    /**
     * Local data source - database
     */
    private final LocalDataSource mLocalDataSource;

    /**
     * Constructor.
     *
     * @param localDataSource
     * @param remoteDataSource
     */
    private Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (debug)
            Log.d(TAG, "constructor(localDataSource: " + localDataSource + "; remoteDataSource: " + remoteDataSource + ")");

        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    /**
     * Returns instance of the repository.
     *
     * @param remoteDataSource
     * @return
     */
    public static Repository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new Repository(localDataSource, remoteDataSource);
        }

        return sInstance;
    }

    @Override
    public void getMovies(final int sort, final DataSource.LoadItemsListCallback<Movie> listener) {
        if (debug) Log.d(TAG, "getMovies(sort: " + sort + "; listener: " + listener + ")");

        List<Movie> cachedList = sMoviesListCache.get(sort);
        if (cachedList != null) {
            listener.onSuccess(sMoviesListCache.get(sort));
        } else {
            mRemoteDataSource.getMovies(sort, new DataSource.LoadItemsListCallback<Movie>() {
                @Override
                public void onSuccess(List<Movie> movies) {
                    sMoviesListCache.put(sort, movies);
                    listener.onSuccess(movies);
                }

                @Override
                public void onError(String message) {
                    listener.onError(message);
                }
            });
        }
    }

    @Override
    public void getMovieDetails(final long movieId, final DataSource.LoadItemCallback<Movie> listener) {
        if (debug)
            Log.d(TAG, "getMovieDetails(movieId: " + movieId + "; listener: " + listener + ")");

        Movie cachedMovie = sMoviesDetailsCache.get(movieId);
        if (cachedMovie != null) {
            listener.onSuccess(cachedMovie);
        } else {
            mRemoteDataSource.getMovieDetails(movieId, new DataSource.LoadItemCallback<Movie>() {
                @Override
                public void onSuccess(Movie movie) {
                    sMoviesDetailsCache.put(movieId, movie);
                    listener.onSuccess(movie);
                }

                @Override
                public void onError(String message) {
                    listener.onError(message);
                }
            });
        }
    }

    @Override
    public void getFavoriteMovies(DataSource.LoadItemsListCallback<Movie> callback) {
        if (debug) Log.d(TAG, "getFavoriteMovies(callback: " + callback + ")");

        mLocalDataSource.getFavoriteMovies(callback);
    }

    @Override
    public void addFavoriteMovie(Movie movie) {
        if (debug) Log.d(TAG, "addFavoriteMovie(movie: " + movie + ")");

        mLocalDataSource.addFavoriteMovie(movie);
    }

    @Override
    public void removeFavoriteMovie(long movieId) {
        if (debug) Log.d(TAG, "removeFavoriteMovie(movieId: " + movieId + ")");

        mLocalDataSource.removeFavoriteMovie(movieId);
    }

    @Override
    public void deleteAllFavoriteMovies() {
        if (debug) Log.d(TAG, "deleteAllFavoriteMovies()");

        mLocalDataSource.deleteAllFavoriteMovies();
    }
}
