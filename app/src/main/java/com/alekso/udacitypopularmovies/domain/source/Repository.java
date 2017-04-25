package com.alekso.udacitypopularmovies.domain.source;

import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alekso on 26/02/2017.
 */

public class Repository implements LocalDataSource, RemoteDataSource {

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
}
