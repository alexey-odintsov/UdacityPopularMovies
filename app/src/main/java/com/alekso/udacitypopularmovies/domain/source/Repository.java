package com.alekso.udacitypopularmovies.domain.source;

/**
 * Created by alekso on 26/02/2017.
 */

public class Repository implements DataSource {

    /**
     * Repository instance field
     */
    private static Repository sInstance;

    /**
     * Remote data source instance
     */
    private final DataSource mRemoteDataSource;

    /**
     * Constructor.
     *
     * @param remoteDataSource
     */
    private Repository(DataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    /**
     * Returns instance of the repository.
     *
     * @param remoteDataSource
     * @return
     */
    public static Repository getInstance(DataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new Repository(remoteDataSource);
        }

        return sInstance;
    }

    @Override
    public void getMovies(int sort, LoadMoviesListener listener) {
        mRemoteDataSource.getMovies(sort, listener);
    }

    @Override
    public void getMovieDetails(long movieId, LoadMovieDetailsListener listener) {
        mRemoteDataSource.getMovieDetails(movieId, listener);
    }
}
