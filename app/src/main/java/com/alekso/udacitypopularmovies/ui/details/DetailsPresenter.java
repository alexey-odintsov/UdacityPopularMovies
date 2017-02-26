package com.alekso.udacitypopularmovies.ui.details;

import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.model.Movie;

/**
 * Created by alekso on 26/02/2017.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private final Repository mRepository;
    private final DetailsContract.View mView;
    private long mMovieId;

    /**
     * @param movieId
     * @param repository
     * @param view
     */
    public DetailsPresenter(long movieId, Repository repository, DetailsContract.View view) {
        mMovieId = movieId;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMovie();
    }

    private void loadMovie() {
        mView.showLoadingIndicator();
        mRepository.getMovieDetails(mMovieId, new DataSource.LoadMovieDetailsListener() {
            @Override
            public void onSuccess(Movie movie) {
                mView.showMovieInfo(movie);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
